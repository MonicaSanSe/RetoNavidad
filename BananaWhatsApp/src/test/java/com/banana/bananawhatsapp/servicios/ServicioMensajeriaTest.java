package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioMensajeriaTest {

     @Autowired
     private IServicioMensajeria servicio;
     @Autowired
     private IUsuarioRepository repoUsu;


    @Test
    void testBeans(){
    assertNotNull(servicio);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() throws SQLException {

        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = repoUsu.buscar(2);

        assertNotNull(servicio.enviarMensaje(usuario1, usuario2,"mensaje de prueba" ));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = repoUsu.buscar(2);

        assertThrows(MensajeException.class, () -> {
            servicio.enviarMensaje(usuario1, usuario2,"hola" );
        });

    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = repoUsu.buscar(2);

        assertNotNull(servicio.mostrarChatConUsuario(usuario2, usuario1));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = null;

        assertThrows(MensajeException.class, () -> {
            servicio.mostrarChatConUsuario(usuario1, usuario2 );
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = repoUsu.buscar(2);

        assertTrue(servicio.borrarChatConUsuario(usuario2, usuario1));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = null;

        assertFalse(servicio.borrarChatConUsuario(usuario2, usuario1));
    }

}