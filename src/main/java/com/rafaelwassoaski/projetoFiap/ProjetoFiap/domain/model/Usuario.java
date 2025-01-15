package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import lombok.Builder;



@Builder
public class Usuario {
    private Integer id;
    private String email;
    private String senha;
    private Papel papel;

    public Usuario() {
    }


    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.papel = Papel.FUNCIONARIO;
    }

    public Usuario(Integer id, String email, String senha, Papel papel) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
