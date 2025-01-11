package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

public class Bebida extends Item {

    public Bebida() {
    }

    public Bebida(String nome, double preco)  {
        super(nome, preco);
    }

    public Bebida(int id, String nome, double preco) {
        super(id, nome, preco);
    }

    public Bebida(String nome, double preco, String categoria)  {
        super(nome, preco, categoria);
    }

    public Bebida(int id, String nome, double preco, String categoria) {
        super(id, nome, preco, categoria);
    }
}
