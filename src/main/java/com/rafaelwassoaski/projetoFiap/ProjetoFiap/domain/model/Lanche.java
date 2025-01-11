package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

public class Lanche extends Item{

    public Lanche() {
    }

    public Lanche(String nome, double preco) {
        super(nome, preco);
    }

    public Lanche(int id, String nome, double preco) {
        super(id, nome, preco);
    }

    public Lanche(String nome, double preco, String categoria) {
        super(nome, preco, categoria);
    }

    public Lanche(int id, String nome, double preco, String categoria) {
        super(id, nome, preco, categoria);
    }


}
