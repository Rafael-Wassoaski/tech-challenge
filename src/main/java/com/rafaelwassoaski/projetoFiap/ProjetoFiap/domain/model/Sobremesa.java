package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

public class Sobremesa extends Item{
    public Sobremesa() {
    }

    public Sobremesa(String nome, double preco) {
        super(nome, preco);
    }

    public Sobremesa(int id, String nome, double preco) {
        super(id, nome, preco);
    }

    public Sobremesa(String nome, double preco, String categoria) {
        super(nome, preco, categoria);
    }

    public Sobremesa(int id, String nome, double preco, String categoria) {
        super(id, nome, preco, categoria);
    }
}
