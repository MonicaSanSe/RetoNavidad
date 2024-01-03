package com.banana.bananawhatsapp.servicios;

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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("dev")
class ServicioMensajeriaTest {

     @Autowired
     private IServicioMensajeria servicio;
     @Autowired
     private IUsuarioRepository repoUsu;
      @Autowired
     private IMensajeRepository repoMen;

     @Autowired
    private ApplicationContext context;


    @Test
    void testBeans(){
     assertNotNull(servicio);
     assertNotNull(context);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() throws SQLException {

        Usuario usuCre1 = new Usuario(null,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaBorrar2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);

        Mensaje mens = servicio.enviarMensaje(usuario1, usuario2,"mensaje de prueba" );
        assertNotNull(mens);
        assertThat(mens.getId(), greaterThan(0));
        assertEquals(mens.getCuerpo(),"mensaje de prueba");
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaBorrar2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);

        assertThrows(MensajeException.class, () -> {
            servicio.enviarMensaje(usuario1, usuario2,"hola" );
        });

    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaBorrar2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);
        Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje prueba repoBorrar", LocalDate.now());
        Mensaje mens1 = repoMen.crear(mens);

        List<Mensaje> listMens = servicio.mostrarChatConUsuario(usuario1, usuario2);
        assertNotNull(listMens);
        assertThat(listMens.get(0).getId(), greaterThan(0));
        assertEquals(listMens.get(0).getCuerpo(),"mensaje prueba repoBorrar");

    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() throws SQLException {
        Usuario usuario1 = null;
        Usuario usuario2 = null;

        assertThrows(NullPointerException.class, () -> {
            servicio.mostrarChatConUsuario(usuario1, usuario2 );
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaBorrar2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);
        Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje prueba servi mensaje borrar", LocalDate.now());
        Mensaje mens1 = repoMen.crear(mens);

        assertTrue(servicio.borrarChatConUsuario(usuario1, usuario2));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() throws SQLException {
        Usuario usuario1 = null;
        Usuario usuario2 = null;

        assertThrows(NullPointerException.class, () -> {
            servicio.borrarChatConUsuario(usuario2, usuario1);
        });
    }

}