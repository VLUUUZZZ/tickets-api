package com.example.tickets.dto;

import com.example.tickets.model.PrioridadTicket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTicketRequest(
        @NotNull(message = "El usuarioId es obligatorio") Long usuarioId,
        @NotBlank(message = "La descripción no puede estar vacía") String descripcion,
        @NotNull(message = "La prioridad es obligatoria (BAJA, MEDIA, ALTA)") PrioridadTicket prioridad) {
}
