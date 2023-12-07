package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
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
class MensajeRepositoryTest {

    @Autowired
    private IMensajeRepository repo;
    @Autowired
    private IUsuarioRepository repoUsu;

    @Test
    void testBeans() {
        assertNotNull(repo);
    }

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = repoUsu.buscar(2);
        Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje prueba repo", LocalDate.now());

        assertNotNull(repo.crear(mens));

    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
        Usuario usuario2 = repoUsu.buscar(2);
        Mensaje mens = new Mensaje(null,usuario1, usuario2,"12", LocalDate.now());

        assertThrows(SQLException.class, () -> {
            repo.crear(mens);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {
         Usuario usuario1 = repoUsu.buscar(1);
         Usuario usuario2 = repoUsu.buscar(2);
         assertNotNull(repo.obtener(usuario1,usuario2));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
         Usuario usuario1 = null;
         Usuario usuario2 = null;
         assertThrows(SQLException.class, () -> {
            repo.obtener(usuario1,usuario2);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException {
        Usuario usuario1 = repoUsu.buscar(1);
         Usuario usuario2 = repoUsu.buscar(2);
         assertTrue(repo.borrarTodos(usuario1,usuario2));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws SQLException {

        Usuario usuario1 = null;
         Usuario usuario2 = null;
         assertFalse(repo.borrarTodos(usuario1,usuario2));
    }

}