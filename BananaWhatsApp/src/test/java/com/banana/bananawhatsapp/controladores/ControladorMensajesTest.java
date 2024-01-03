package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("dev")
class ControladorMensajesTest {


    @Autowired
    private ControladorMensajes controladorMensajes;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private IUsuarioRepository repoUsu;
    @Autowired
    private IMensajeRepository repoMens;
    @Test
    void testBeans() {
     assertNotNull(controladorMensajes);
     assertNotNull(context);
    }
    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaEnvio2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);

        assertTrue(controladorMensajes.enviarMensaje(usuario1.getId(),usuario2.getId(),"Prueba controlador"));

    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);

        assertThrows(Exception.class, () -> {
            controladorMensajes.enviarMensaje(usuario1.getId(),null,"hola" );
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaEnvio2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);
        Boolean mens = controladorMensajes.enviarMensaje(usuario1.getId(),usuario2.getId(),"Prueba controlador");

        assertTrue(controladorMensajes.mostrarChat(usuario1.getId(),usuario2.getId()));

    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);

        assertThrows(Exception.class, () -> {
            controladorMensajes.mostrarChat(usuario1.getId(),null );
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaEnvio2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);
        Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje prueba eliminar controlador mensajes", LocalDate.now());
        Mensaje mens1 = repoMens.crear(mens);

        assertTrue(controladorMensajes.eliminarChatConUsuario(usuario1.getId(),usuario2.getId()));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);

        assertThrows(NullPointerException.class, () -> {
            controladorMensajes.eliminarChatConUsuario(usuario1.getId(),null);
        });
    }
}