package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidadorItem;

public class Acompanhamento extends Item{


    public Acompanhamento(String nome, double preco) {
        super(nome, preco);
    }

    public Acompanhamento(int id, String nome, double preco) {
        super(id, nome, preco);
    }

    public Acompanhamento(String nome, double preco, String categoria) {
        super(nome, preco, categoria);
    }

    public Acompanhamento(int id, String nome, double preco, String categoria) {
        super(id, nome, preco, categoria);
    }
}
