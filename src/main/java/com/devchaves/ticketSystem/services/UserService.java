package com.devchaves.ticketSystem.services;

import com.devchaves.ticketSystem.DTOS.UserCreateDTO;
import com.devchaves.ticketSystem.DTOS.UserResponseDTO;
import com.devchaves.ticketSystem.models.UserModel;
import com.devchaves.ticketSystem.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseEntity userLogin(UserCreateDTO userDTO){

        validateLoginCredentials(userDTO);

        UserModel user = findValidateUser(userDTO);

        String token = this.tokenService.generateToken(user);

        return new ResponseEntity(new UserResponseDTO(user.getUsersName(), token), HttpStatus.OK);

    }

    private void validateLoginCredentials(UserCreateDTO userDTO){
        if(userDTO.getUsersName() == null || userDTO.getUsersPass() == null){
            throw new RuntimeException("Invalid Credentials");
        }
    }

    private UserModel findValidateUser(UserCreateDTO userDTO){
        var user = userRepository.findUserByUserName(userDTO.getUsersName()).orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(userDTO.getUsersPass(), user.getUsersPass())){
            throw new RuntimeException("Invalid Credentials");
        }

        return user;

    }



}
