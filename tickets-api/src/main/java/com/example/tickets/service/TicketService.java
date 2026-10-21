package com.example.tickets.service;

import com.example.tickets.dto.CrearTicketRequest;
import com.example.tickets.dto.ReasignarPrioridadRequest;
import com.example.tickets.dto.TicketResponse;
import com.example.tickets.exception.RecursoNoEncontradoException;
import com.example.tickets.exception.ReglaNegocioException;
import com.example.tickets.model.EstadoTicket;
import com.example.tickets.model.Ticket;
import com.example.tickets.model.Usuario;
import com.example.tickets.repository.TicketRepository;
import com.example.tickets.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** 1. crearTicket */
    @Transactional
    public TicketResponse crearTicket(CrearTicketRequest request) {
        Usuario usuario = buscarUsuario(request.usuarioId());

        if (!usuario.isActivo()) {
            throw new ReglaNegocioException("El usuario con id " + usuario.getId() + " está inactivo");
        }
        if (request.descripcion() == null || request.descripcion().isBlank()) {
            throw new ReglaNegocioException("La descripción no puede estar vacía");
        }

        Ticket ticket = new Ticket();
        ticket.setDescripcion(request.descripcion().trim());
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(request.prioridad());
        ticket.setUsuario(usuario);

        return TicketResponse.from(ticketRepository.save(ticket));
    }

    /** 2. actualizarEstadoTicket: ABIERTO -> EN_PROCESO -> CERRADO */
    @Transactional
    public TicketResponse actualizarEstadoTicket(Long ticketId) {
        Ticket ticket = buscarTicket(ticketId);

        switch (ticket.getEstado()) {
            case ABIERTO -> ticket.setEstado(EstadoTicket.EN_PROCESO);
            case EN_PROCESO -> ticket.setEstado(EstadoTicket.CERRADO);
            case CERRADO -> throw new ReglaNegocioException(
                    "El ticket " + ticketId + " está CERRADO y no se puede modificar");
        }

        return TicketResponse.from(ticketRepository.save(ticket));
    }

    /** 3. reasignarPrioridad */
    @Transactional
    public TicketResponse reasignarPrioridad(Long ticketId, ReasignarPrioridadRequest request) {
        Ticket ticket = buscarTicket(ticketId);

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new ReglaNegocioException(
                    "No se puede cambiar la prioridad: el ticket " + ticketId + " está CERRADO");
        }

        ticket.setPrioridad(request.prioridad());
        return TicketResponse.from(ticketRepository.save(ticket));
    }

    /** 5. obtenerTicketsUsuario */
    @Transactional(readOnly = true)
    public List<TicketResponse> obtenerTicketsUsuario(Long usuarioId) {
        buscarUsuario(usuarioId);
        return ticketRepository.findByUsuarioId(usuarioId).stream()
                .map(TicketResponse::from)
                .toList();
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con id " + id + " no existe"));
    }

    private Ticket buscarTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El ticket con id " + id + " no existe"));
    }
}
