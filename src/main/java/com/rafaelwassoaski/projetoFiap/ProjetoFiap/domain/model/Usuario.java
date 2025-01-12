package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidardorEmail;

public class Usuario {
    private Integer id;
    private String email;
    private String cpf;
    private String senha;
    private Papel papel;

    public Usuario() {
    }

    public Usuario(String email, String cpf) {
        this.email = email;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(String email, String senha, String cpf) {
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(Integer id, String email, String senha, String cpf) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(int id, String email, String senha, String cpf, Papel papel) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
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

    public String getCpf() {
        return cpf;
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

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
