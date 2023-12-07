package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import java.sql.SQLException;

@Setter
@Service
public class ServicioUsuarios implements IServicioUsuarios{
    @Autowired
    private IUsuarioRepository repoUsuario;

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        Usuario usu = null;
        if (usuario.valido()) {
            try {
                usu = repoUsuario.crear(usuario);
            } catch (SQLException e) {
                throw new UsuarioException("Usuario no creado");
            }
        }
        return usu;
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        Boolean borrado = false;
        try {
            borrado = repoUsuario.borrar(usuario);
        } catch (SQLException e) {
            throw new UsuarioException("Usuario no borrado");
        }
        return borrado;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        Usuario usu = null;
        if (usuario.valido()) {
            try {
                usu = repoUsuario.actualizar(usuario);
            } catch (SQLException e) {
                throw new UsuarioException("Usuario no actualizado");
            }
        }
        return usu;
    }

    @Override
    public Usuario obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        Set<Usuario> usuarios = new HashSet<>();
        Usuario usu = null;
        try {
            usuarios = repoUsuario.obtenerPosiblesDestinatarios(usuario.getId(), 20);
            usuario = usuarios.iterator().next();
        } catch (SQLException e) {
            throw new UsuarioException("Destinatarios no encontrados");
        }

        return usu;
    }
}
