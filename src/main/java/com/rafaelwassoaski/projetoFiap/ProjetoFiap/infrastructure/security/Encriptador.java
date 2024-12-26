package com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.Base64;

public class Encriptador {

    private PasswordEncoder encoder;

    public Encriptador(String sal) {
        this.encoder = new StandardPasswordEncoder(sal);

    }

    public String encriptarTexto(String texto){
        return encoder.encode(texto);
    }

    public boolean senhasBatem(String senhaOriginal, String senhaForte){
        return encoder.matches(senhaOriginal, senhaForte);
    }
}
