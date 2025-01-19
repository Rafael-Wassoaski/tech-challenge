package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto;

public class TokenDTO {
    private String username;
    private String token;

    public TokenDTO() {
    }

    public TokenDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}