package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import lombok.Builder;



@Builder
public class Usuario {
    private Integer id;
    private String email;
    private String nome;
    private String cpf;
    private String senha;
    private Papel papel;

    public Usuario() {
    }

    public Usuario(String cpf) {
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(String cpf, String senha) {
        this.senha = senha;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(String email, String nome, String cpf) {
        this.email = email;
        this.nome = nome;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(String email, String nome, String senha, String cpf) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(Integer id, String email, String nome, String senha, String cpf) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = Papel.CLIENTE;
    }

    public Usuario(int id, String email, String nome, String senha, String cpf, Papel papel) {
        this.id = id;
        this.email = email;
        this.nome = nome;
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

    public String getNome() {
        return nome;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
