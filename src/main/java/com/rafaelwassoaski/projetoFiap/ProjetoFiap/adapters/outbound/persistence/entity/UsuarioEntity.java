package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import jakarta.persistence.*;

@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String email;
    private String nome;
    @Column(nullable = true)
    private String senha;
    @Column
    private String cpf;
    @Enumerated(EnumType.STRING)
    @Column
    private Papel papel;

    public UsuarioEntity(int id, String email,String nome, String senha, String cpf, Papel papel) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = papel;
    }

    public UsuarioEntity(String email, String nome, String senha, String cpf, Papel papel) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = papel;
    }

    public UsuarioEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
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
}
