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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MensajeRepositoryTest {

    @Autowired
    private IMensajeRepository repo;
    @Autowired
    private IUsuarioRepository repoUsu;

    @Autowired
    private ApplicationContext context;

    @Test
    void testBeans() {
        assertNotNull(context);
        assertNotNull(repo);
    }

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaEnvio1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaEnvio2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);

        Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje prueba repo", LocalDate.now());

        assertNotNull(repo.crear(mens));
        assertThat(mens.getId(), greaterThan(0));
        assertEquals(mens.getCuerpo(),"mensaje prueba repo");
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() throws SQLException {

        Mensaje mens = new Mensaje(null,null, null,null, LocalDate.now());

        assertThrows(NullPointerException.class, () -> {
            repo.crear(mens);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {
         Usuario usuCre1 = new Usuario(null,"MonicaObtener1","monicass@gmail.com", LocalDate.now(), true);
         Usuario usuario1 = repoUsu.crear(usuCre1);
         Usuario usuCre2 = new Usuario(null,"MonicaObtener2","monicass@gmail.com", LocalDate.now(), true);
         Usuario usuario2 = repoUsu.crear(usuCre2);
         Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje lalala", LocalDate.now());
         Mensaje enviado = repo.crear(mens);

         List<Mensaje> listMens = repo.obtener(usuario1,usuario2);

         assertNotNull(listMens);
         assertThat(listMens.get(0).getId(), greaterThan(0));
         assertEquals(listMens.size(), 1);
         assertEquals(listMens.get(0).getCuerpo(),"mensaje lalala");
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
         Usuario usuario1 = null;
         Usuario usuario2 = null;
         assertThrows(NullPointerException.class, () -> {
            repo.obtener(usuario1,usuario2);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException {
        Usuario usuCre1 = new Usuario(null,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario1 = repoUsu.crear(usuCre1);
        Usuario usuCre2 = new Usuario(null,"MonicaBorrar2","monicass@gmail.com", LocalDate.now(), true);
        Usuario usuario2 = repoUsu.crear(usuCre2);
        Mensaje mens = new Mensaje(null,usuario1, usuario2,"mensaje prueba repoBorrar", LocalDate.now());
        Mensaje mens1 = repo.crear(mens);
        assertTrue(repo.borrarTodos(usuario1,usuario2));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws SQLException {

        Usuario usuario1 = new Usuario(999999,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
         Usuario usuario2 = new Usuario(999998,"MonicaBorrar1","monicass@gmail.com", LocalDate.now(), true);
         assertFalse(repo.borrarTodos(usuario1,usuario2));
    }

}