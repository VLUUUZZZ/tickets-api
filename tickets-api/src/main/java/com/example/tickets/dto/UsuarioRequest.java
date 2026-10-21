package com.example.tickets.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank(message = "El nombre no puede estar vacío") String nombre) {
}
