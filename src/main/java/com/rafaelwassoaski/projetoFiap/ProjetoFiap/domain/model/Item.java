package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

public abstract class Item {

    private String nome;
    private double preco;
    private String categoria;
    private int id;

    public Item() {
    }

    public Item(int id, String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.id = id;
    }

    public Item(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Item(int id, String nome, double preco, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.id = id;
        this.categoria = categoria;
    }

    public Item(String nome, double preco, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
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

    public String getCategoria() {
        return categoria;
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
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
