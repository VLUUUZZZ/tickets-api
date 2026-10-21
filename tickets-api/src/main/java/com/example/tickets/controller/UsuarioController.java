package com.example.tickets.controller;

import com.example.tickets.dto.TicketResponse;
import com.example.tickets.dto.UsuarioRequest;
import com.example.tickets.dto.UsuarioResponse;
import com.example.tickets.service.TicketService;
import com.example.tickets.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TicketService ticketService;

    public UsuarioController(UsuarioService usuarioService, TicketService ticketService) {
        this.usuarioService = usuarioService;
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.crearUsuario(request);
    }

    @PatchMapping("/{id}/desactivar")
    public UsuarioResponse desactivar(@PathVariable Long id) {
        return usuarioService.desactivarUsuario(id);
    }

    @GetMapping("/{id}/tickets")
    public List<TicketResponse> tickets(@PathVariable Long id) {
        return ticketService.obtenerTicketsUsuario(id);
    }
}
