package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({PropertiesConfigProd.class, PropertiesConfigDev.class, ReposConfig.class, ServicesConfig.class})
@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia", "com.banana.bananawhatsapp.servicios", "com.banana.bananawhatsapp.controladores"})
//@PropertySource("classpath:application-prod.properties")
public class SpringConfig {
}
