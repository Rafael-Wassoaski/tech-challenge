package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

public class Cliente {

    private Integer id;
    private String cpf;
    private String email;
    private String nome;


    public Cliente() {
    }

    public Cliente(String cpf) {
        this.cpf = cpf;
    }

    public Cliente(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public Cliente(Integer id, String cpf, String email, String nome) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
