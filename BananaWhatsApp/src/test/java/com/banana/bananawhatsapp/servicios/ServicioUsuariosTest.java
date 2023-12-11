package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioUsuariosTest {

    @Autowired
    private IServicioUsuarios servicio;

    @Autowired
    private IUsuarioRepository repoUsu;

    @Autowired
    private ApplicationContext context;

    @Test
    void testBeans() {
     assertNotNull(servicio);
     assertNotNull(context);
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() {
        Usuario usu = new Usuario(null,"Monica","monicass@gmail.com", LocalDate.now(), true);
        assertNotNull(servicio.crearUsuario(usu));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() {
        Usuario usu = new Usuario(null,"","monicass@gmail.com", LocalDate.now(), true);
        assertThrows(UsuarioException.class, () -> {
            servicio.crearUsuario(usu);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() throws SQLException {
        Usuario usuCre = new Usuario(null,"MonicaSanta","monicass@gmail.com", LocalDate.now(), true);
        Usuario usu = servicio.crearUsuario(usuCre);
        assertTrue(servicio.borrarUsuario(usu));

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
        Usuario usu = new Usuario(0,"MonicaSanta","monicass@gmail.com", LocalDate.now(), true);
        assertFalse(servicio.borrarUsuario(usu));
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
        Usuario usuCre = new Usuario(null,"MonicaSanta","monicass@gmail.com", LocalDate.now(), true);
        Usuario usu = servicio.crearUsuario(usuCre);

        Usuario usuAct = new Usuario(usu.getId(),"MonicaServ1","monicass@gmail.com", LocalDate.now(), true);
        assertNotNull(servicio.actualizarUsuario(usuAct));

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
        Usuario usu = new Usuario(1,"Monica","monicassgmail.com", LocalDate.now(), true);

         assertThrows(UsuarioException.class, () -> {
            servicio.actualizarUsuario(usu);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuarioValido() throws SQLException {
        Usuario usuCrea = new Usuario(null,"Monica","monicass@gmail.com", LocalDate.now(), true);
        Usuario usu = servicio.crearUsuario(usuCrea);
        assertNotNull(servicio.obtenerPosiblesDesinatarios(usu,20));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {
        Usuario usu = new Usuario(null,"Monica","monicass@gmail.com", LocalDate.now(), true);

         assertThrows(NullPointerException.class, () -> {
            servicio.obtenerPosiblesDesinatarios(usu,20);
        });

    }
}