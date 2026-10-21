package com.example.tickets.dto;

import com.example.tickets.model.PrioridadTicket;
import jakarta.validation.constraints.NotNull;

public record ReasignarPrioridadRequest(
        @NotNull(message = "La prioridad es obligatoria (BAJA, MEDIA, ALTA)") PrioridadTicket prioridad) {
}
