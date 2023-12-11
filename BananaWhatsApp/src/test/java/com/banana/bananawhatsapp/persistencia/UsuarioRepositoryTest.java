package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
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
class UsuarioRepositoryTest {
    @Autowired
    private IUsuarioRepository repo;

    @Autowired
    private ApplicationContext context;
    @Test
    void testBeans() {
        assertNotNull(context);
        assertNotNull(repo);
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        Usuario usu = new Usuario(null,"Monica","monicass@gmail.com", LocalDate.now(), true);
        assertNotNull(repo.crear(usu));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() throws SQLException{
        Usuario usu = new Usuario(null,"","monicass@gmail.com", null, true);
        assertThrows(NullPointerException.class, () -> {
            repo.crear(usu);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException {

       Usuario usuCre = new Usuario(1,"MonicaSS","monicass@gmail.com", LocalDate.now(), true);
       Usuario usu = repo.crear(usuCre);
       Usuario usuAct = new Usuario(usu.getId(),"Monica","monicass@gmail.com", LocalDate.now(), true);
        assertNotNull(repo.actualizar(usuAct));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws SQLException {
        Usuario usu = new Usuario(99999,"Monica","monicass@gmail.com", LocalDate.now(), true);

        assertThrows(UsuarioException.class, () -> {
            repo.actualizar(usu);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException{
       Usuario usuCre = new Usuario(1,"MonicaSS","monicass@gmail.com", LocalDate.now(), true);
       Usuario usu = repo.crear(usuCre);
       Usuario usuBorr = new Usuario(usu.getId(),"Monica","monicass@gmail.com", LocalDate.now(), true);

        assertTrue(repo.borrar(usuBorr));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws SQLException{
        Usuario usu= new Usuario(999,"Monica","monicassgmail.com", LocalDate.now(), true);
        assertFalse(repo.borrar(usu));
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() throws SQLException {
        assertNotNull(repo.obtenerPosiblesDestinatarios(1,20));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() throws SQLException{
        assertThrows(NullPointerException.class, () -> {
            repo.obtenerPosiblesDestinatarios(null,0);
        });
    }

}