package com.example.tickets.dto;

import com.example.tickets.model.Usuario;

public record UsuarioResponse(Long id, String nombre, boolean activo) {
    public static UsuarioResponse from(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.isActivo());
    }
}
