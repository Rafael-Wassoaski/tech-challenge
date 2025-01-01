package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidardorEmail;

public class Usuario {
    private String email;
    private String senha;
    private Papel papel;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(String email, String senha, Papel papel) {
        this.email = email;
        this.senha = senha;
        this.papel = papel;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }
}
