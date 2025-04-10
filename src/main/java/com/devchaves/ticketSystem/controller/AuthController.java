package com.devchaves.ticketSystem.controller;

import com.devchaves.ticketSystem.DTOS.UsersDTO.UserCreateDTO;
import com.devchaves.ticketSystem.DTOS.UsersDTO.UserRegisterDTO;
import com.devchaves.ticketSystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.userLogin(userCreateDTO);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserRegisterDTO userDTO){
        return userService.userRegister(userDTO);
    }

}
