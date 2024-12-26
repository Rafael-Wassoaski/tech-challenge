package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.financial.SomatoriaItem;

import java.util.Optional;

public class Pedido {

    private Optional<Lanche> optionalLanche;
    private Optional<Bebida> optionalBebida;
    private Optional<Acompanhamento> optionalAcompanhamento;
    private Optional<Sobremesa> optionalSobremesa;
    private Usuario usuario;

    public Pedido() {}

    public Pedido(Optional<Lanche> lanche, Optional<Bebida> bebida, Optional<Acompanhamento> acompanhamento, Optional<Sobremesa>  sobremesa) {
        this.optionalLanche = lanche;
        this.optionalBebida = bebida;
        this.optionalAcompanhamento = acompanhamento;
        this.optionalSobremesa = sobremesa;
        ;
    }

    public Optional<Lanche> getLanche() {
        return optionalLanche;
    }

    public Optional<Bebida> getBebida() {
        return optionalBebida;
    }

    public Optional<Acompanhamento> getAcompanhamento() {
        return optionalAcompanhamento;
    }

    public Optional<Sobremesa> getSobremesa() {
        return optionalSobremesa;
    }

    public double getTotal() {
        SomatoriaItem somatoriaItem = new SomatoriaItem();

        somatoriaItem.adiconarItemASoma(this.optionalLanche);
        somatoriaItem.adiconarItemASoma(this.optionalAcompanhamento);
        somatoriaItem.adiconarItemASoma(this.optionalBebida);
        somatoriaItem.adiconarItemASoma(this.optionalSobremesa);

        return somatoriaItem.getValorTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
