package com.devchaves.ticketSystem.services;

import com.devchaves.ticketSystem.DTOS.TicketDTO.TicketRequestToFinish;
import com.devchaves.ticketSystem.DTOS.UsersDTO.UserCreateDTO;
import com.devchaves.ticketSystem.models.TicketModel;
import com.devchaves.ticketSystem.models.UserModel;
import com.devchaves.ticketSystem.repositories.TicketRepository;
import com.devchaves.ticketSystem.repositories.UserRepository;
import com.devchaves.ticketSystem.util.VerifyRole.ValidateAdminAccess;
import com.devchaves.ticketSystem.util.converterDTOLogic.ConverseDTO;
import com.devchaves.ticketSystem.util.converters.TicketModelToResponseConverter;
import com.devchaves.ticketSystem.util.converters.UserDefaultToResponse;
import com.devchaves.ticketSystem.util.converters.UserRequestToModelConverter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

import static com.devchaves.ticketSystem.models.RoleEnum.ADMIN;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TicketRepository ticketRepository;
    private final ValidateAdminAccess checker;
    private final ConverseDTO converseDTO;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder, TicketRepository ticketRepository, ValidateAdminAccess checker, ConverseDTO converseDTO) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ticketRepository = ticketRepository;
        this.checker = checker;
        this.converseDTO = converseDTO;
    }

    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userDTO){

        UserModel currentUser = checker.checker(null);

        if(currentUser.getUsersRole() != ADMIN){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }

        if(userDTO.getUsersName() == null || userDTO.getUsersPass() == null){
            return ResponseEntity.badRequest().body(null);
        }

        var userDB = new UserModel();

        UserRequestToModelConverter converter = new UserRequestToModelConverter(passwordEncoder);

        userDB = converter.convert(userDTO);

        UserDefaultToResponse converterTeste = new UserDefaultToResponse(passwordEncoder);

        var response = converterTeste.convert(userDB);

        userRepository.save(userDB);

        return ResponseEntity.ok().body(response);

    }

    public ResponseEntity<?> updateTicketStatus(@PathVariable(value="ticketId")UUID ticketId,@RequestBody @Valid TicketRequestToFinish ticketDTO){

        UserModel currentUser = checker.checker(null);

        if(currentUser.getUsersRole() != ADMIN){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }

        if(ticketDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<TicketModel> ticket = ticketRepository.findById(ticketId);

        if(!ticket.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Not Found!");
        }

        TicketModel ticketUpdate = ticket.get();

        ticketUpdate.setTicket_status(ticketDTO.getTicketStatus());

        ticketRepository.save(ticketUpdate);

        TicketModelToResponseConverter converter = new TicketModelToResponseConverter();

        var response = converter.convert(ticketUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
