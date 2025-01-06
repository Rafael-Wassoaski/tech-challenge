package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String username;
    private String token;

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