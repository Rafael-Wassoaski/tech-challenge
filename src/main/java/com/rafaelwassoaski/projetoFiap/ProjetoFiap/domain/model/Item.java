package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidadorItem;

public abstract class Item {

    private String nome;
    private double preco;
    private int id;

    public Item(int id, String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.id = id;
    }

    public Item(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }
}
