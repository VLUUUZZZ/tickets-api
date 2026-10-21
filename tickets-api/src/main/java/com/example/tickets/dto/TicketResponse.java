package com.example.tickets.dto;

import com.example.tickets.model.EstadoTicket;
import com.example.tickets.model.PrioridadTicket;
import com.example.tickets.model.Ticket;

public record TicketResponse(Long id, String descripcion, EstadoTicket estado,
                             PrioridadTicket prioridad, Long usuarioId) {
    public static TicketResponse from(Ticket t) {
        return new TicketResponse(t.getId(), t.getDescripcion(), t.getEstado(),
                t.getPrioridad(), t.getUsuario().getId());
    }
}
