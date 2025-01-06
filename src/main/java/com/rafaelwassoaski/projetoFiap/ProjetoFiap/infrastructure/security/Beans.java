package com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Beans {
    @Value("${custom.sal}")
    private String sal;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Encriptador encriptador() {
        return new Encriptador(sal);
    }
}
