package com.example.tickets.controller;

import com.example.tickets.dto.CrearTicketRequest;
import com.example.tickets.dto.ReasignarPrioridadRequest;
import com.example.tickets.dto.TicketResponse;
import com.example.tickets.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse crear(@Valid @RequestBody CrearTicketRequest request) {
        return ticketService.crearTicket(request);
    }

    @PatchMapping("/{id}/estado")
    public TicketResponse actualizarEstado(@PathVariable Long id) {
        return ticketService.actualizarEstadoTicket(id);
    }

    @PatchMapping("/{id}/prioridad")
    public TicketResponse reasignarPrioridad(@PathVariable Long id,
                                             @Valid @RequestBody ReasignarPrioridadRequest request) {
        return ticketService.reasignarPrioridad(id, request);
    }
}
