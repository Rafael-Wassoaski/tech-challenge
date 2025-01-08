package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import jakarta.persistence.*;



@Entity
public class LancheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String nome;
    @Column
    private double preco;

    public LancheEntity() {
    }

    public LancheEntity(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public LancheEntity(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
