package com.example.tickets.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> mensajes) {
    public static ErrorResponse of(int status, String error, List<String> mensajes) {
        return new ErrorResponse(LocalDateTime.now(), status, error, mensajes);
    }
}
