package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.MensajeJDBCRepository;
import com.banana.bananawhatsapp.persistencia.UsuarioJDBCRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ReposConfig {

    @Value("${db.conn}")
    String dbUrl;

    @Value("${max.conn}")
    Integer maxConn;

    @Bean
    @Profile("dev")
    public MensajeJDBCRepository getMensajesRepositoryJDBCDev() {
        System.out.println("maxConn JDBC:" + maxConn);

        MensajeJDBCRepository repo = new MensajeJDBCRepository();
        repo.setConnUrl(dbUrl);
        return repo;
    }

    @Bean
    @Profile("dev")
    public UsuarioJDBCRepository getUsuariosRepositoryJDBCDev() {
        System.out.println("maxConn JDBC:" + maxConn);

        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setConnUrl(dbUrl);
        return repo;
    }
    @Bean
    @Profile("prod")
    public MensajeJDBCRepository getMensajesRepositoryJDBC() {
        System.out.println("maxConn JDBC:" + maxConn);

        MensajeJDBCRepository repo = new MensajeJDBCRepository();
        repo.setConnUrl(dbUrl);
        return repo;
    }

    @Bean
    @Profile("prod")
    public UsuarioJDBCRepository getUsuariosRepositoryJDBC() {
        System.out.println("maxConn JDBC:" + maxConn);

        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setConnUrl(dbUrl);
        return repo;
    }
}
