package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Setter
//@Service
public class ServicioMensajeria implements IServicioMensajeria {
    @Autowired
    private IMensajeRepository repoMensajes;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        Mensaje mens = new Mensaje(null,remitente,destinatario,texto, LocalDate.now());
        if (mens.valido()) {
            try {
                mens = repoMensajes.crear(mens);
            } catch (SQLException e) {
                throw new MensajeException("Mensaje no enviado");
            }
        }
        return mens;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException, SQLException {
        List<Mensaje> mensajes = null;

        try {
            mensajes = repoMensajes.obtener(remitente, destinatario);
        } catch (SQLException e) {
            throw new SQLException("no hay chats para listar");
        }

        return mensajes;
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        Boolean borrado = false;
        try {
            borrado = repoMensajes.borrarTodos(remitente, destinatario);
        } catch (SQLException e) {
            throw new MensajeException("chats no borrados");
        }
        return borrado;
    }
}
