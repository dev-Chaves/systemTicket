package com.devchaves.ticketSystem.services;

import com.devchaves.ticketSystem.DTOS.TicketDTO.TicketRequestDTO;
import com.devchaves.ticketSystem.DTOS.TicketDTO.TicketResponseDTO;
import com.devchaves.ticketSystem.models.TicketModel;
import com.devchaves.ticketSystem.models.UserModel;
import com.devchaves.ticketSystem.repositories.TicketRepository;
import com.devchaves.ticketSystem.util.converterDTOLogic.ConverseDTO;
import com.devchaves.ticketSystem.util.converters.TIcketRequestToModelConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final ConverseDTO converseDTO;

    public TicketService(TicketRepository ticketRepository, ConverseDTO converseDTO) {
        this.ticketRepository = ticketRepository;
        this.converseDTO = converseDTO;
    }

    public ResponseEntity<TicketResponseDTO> createTicket(TicketRequestDTO ticketDTO){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user;

        if (principal instanceof UserModel) {
            user = (UserModel) principal;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TIcketRequestToModelConverter converter = new TIcketRequestToModelConverter(user);

        TicketModel ticket = converter.convert(ticketDTO);

//        TicketModel ticket = new TicketModel();
//        ticket.setUser(user);
//        ticket.setTicket_title(ticketDTO.getTicketTitle());
//        ticket.setTicket_description(ticketDTO.getTicketDescription());
//        ticket.setTicket_status(ticketDTO.getTicketStatus());
//        ticket.setObservation(ticketDTO.getObservation());
//        ticket.setCreatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        TicketResponseDTO responseDTO = convertToResponseDTO(ticket);

        return ResponseEntity.ok().body(responseDTO);
    }

    private TicketResponseDTO convertToResponseDTO(TicketModel ticket){
        return new TicketResponseDTO(
                ticket.getUser().getUsersName(),
                ticket.getTicket_title(),
                ticket.getTicket_description(),
                ticket.getTicket_status(),
                ticket.getObservation(),
                ticket.getCreatedAt()
        );
    }

}
