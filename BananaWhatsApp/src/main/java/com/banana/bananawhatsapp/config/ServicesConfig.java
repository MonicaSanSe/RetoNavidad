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

@Configuration
public class ServicesConfig {
    @Autowired
    IMensajeRepository mensajeRepo;

    @Autowired
    IUsuarioRepository usuarioRepo;

    @Bean
    public IServicioMensajeria getServicioMensajeria() {
        ServicioMensajeria mens = new ServicioMensajeria();
        mens.setRepoMensajes(mensajeRepo);
        return mens;
    }

    @Bean
    public IServicioUsuarios getServicioUsuarios() {
        ServicioUsuarios usuRepo = new ServicioUsuarios();
        usuRepo.setRepoUsuario(usuarioRepo);
        return usuRepo;
    }
}
