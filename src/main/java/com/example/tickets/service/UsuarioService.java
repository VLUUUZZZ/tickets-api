package com.example.tickets.service;

import com.example.tickets.dto.UsuarioRequest;
import com.example.tickets.dto.UsuarioResponse;
import com.example.tickets.exception.RecursoNoEncontradoException;
import com.example.tickets.model.Usuario;
import com.example.tickets.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = usuarioRepository.save(new Usuario(request.nombre().trim(), true));
        return UsuarioResponse.from(usuario);
    }

    /** 4. desactivarUsuario: activo = false, no elimina datos. */
    @Transactional
    public UsuarioResponse desactivarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con id " + usuarioId + " no existe"));
        usuario.setActivo(false);
        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }
}
