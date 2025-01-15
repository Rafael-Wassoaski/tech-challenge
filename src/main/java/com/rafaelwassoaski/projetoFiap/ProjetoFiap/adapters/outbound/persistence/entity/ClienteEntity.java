package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import jakarta.persistence.*;

@Entity
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String cpf;
    @Column
    private String nome;
    @Column
    private String email;

    public ClienteEntity() {
    }

    public ClienteEntity(String cpf) {
        this.cpf = cpf;
    }

    public ClienteEntity(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public ClienteEntity(Integer id, String cpf) {
        this.id = id;
        this.cpf = cpf;
    }

    public ClienteEntity(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
