package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import jakarta.persistence.*;

@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String email;
    @Column
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column
    private Papel papel;

    public UsuarioEntity(int id, String email, String senha, Papel papel) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
    }

    public UsuarioEntity(String email, String senha, Papel papel) {
        this.email = email;
        this.senha = senha;
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
}
