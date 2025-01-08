package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto;

public class LancheDTO {
    String nomeDoLanche;

    public LancheDTO(String nomeDoLanche) {
        this.nomeDoLanche = nomeDoLanche;
    }

    public String getNomeDoLanche() {
        return nomeDoLanche;
    }
}
