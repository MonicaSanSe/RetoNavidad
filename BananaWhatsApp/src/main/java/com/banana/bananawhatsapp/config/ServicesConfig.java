package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.servicios.IServicioMensajeria;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import com.banana.bananawhatsapp.servicios.ServicioMensajeria;
import com.banana.bananawhatsapp.servicios.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ServicesConfig {
    @Autowired
    IMensajeRepository mensajeRepo;

    @Autowired
    IUsuarioRepository usuarioRepo;

    @Bean
    @Profile("prod")
    public IServicioMensajeria getServicioMensajeria() {
        ServicioMensajeria mens = new ServicioMensajeria();
        mens.setRepoMensajes(mensajeRepo);
        return mens;
    }

    @Bean
    @Profile("prod")
    public IServicioUsuarios getServicioUsuarios() {
        ServicioUsuarios usuRepo = new ServicioUsuarios();
        usuRepo.setRepoUsuario(usuarioRepo);
        return usuRepo;
    }

     @Bean
     @Profile("dev")
    public IServicioMensajeria getServicioMensajeriaDev() {
        ServicioMensajeria mens = new ServicioMensajeria();
        mens.setRepoMensajes(mensajeRepo);
        return mens;
    }

    @Bean
    @Profile("dev")
    public IServicioUsuarios getServicioUsuariosDev() {
        ServicioUsuarios usuRepo = new ServicioUsuarios();
        usuRepo.setRepoUsuario(usuarioRepo);
        return usuRepo;
    }
}
