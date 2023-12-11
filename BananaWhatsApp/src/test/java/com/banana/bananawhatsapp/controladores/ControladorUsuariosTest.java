package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorUsuariosTest {

    @Autowired
    private ControladorUsuarios controladorUsuarios;
    @Autowired
    private ApplicationContext context;

    @Test
    void testBeans() {
     assertNotNull(controladorUsuarios);
     assertNotNull(context);
    }
    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario usu = new Usuario(null, "Monica2", "monicass2@gmail.com", LocalDate.now(),true);
        assertNotNull(controladorUsuarios.alta(usu));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        Usuario usu = new Usuario(null, "Monica2", "monicass2gmail.com", LocalDate.now(),true);

        assertThrows(Exception.class, () -> {
            controladorUsuarios.alta(usu);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
        Usuario usuCre = new Usuario(2, "Monica2", "monicass2@gmail.com", LocalDate.now(),true);
        Usuario usu = controladorUsuarios.alta(usuCre);
        Usuario usuAct = new Usuario(usu.getId(), "Monica22", "monicass22@gmail.com", LocalDate.now(),true);
        assertNotNull(controladorUsuarios.actualizar(usuAct));
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        Usuario usu = new Usuario(2, "Monica2", "monicass2gmail.com", LocalDate.now(),true);

        assertThrows(Exception.class, () -> {
            controladorUsuarios.actualizar(usu);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
        Usuario usu = new Usuario(2, "Monica2", "monicass2@gmail.com", LocalDate.now(),true);
        assertTrue(controladorUsuarios.baja(usu));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        Usuario usu = new Usuario(999, "Monica2", "monicass2gmail.com", LocalDate.now(),true);

        assertFalse(controladorUsuarios.baja(usu));

    }
}