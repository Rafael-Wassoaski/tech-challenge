package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.financial.SomatoriaItem;

import java.util.Optional;

public class Pedido {

    private Optional<Lanche> optionalLanche;
    private Optional<Bebida> optionalBebida;
    private Optional<Acompanhamento> optionalAcompanhamento;
    private Optional<Sobremesa> optionalSobremesa;
    private Usuario usuario;
    private StatusPedido statusPedido;
    private int id;

    public Pedido() {}

    public Pedido(int id, Optional<Lanche> lanche, Optional<Bebida> bebida, Optional<Acompanhamento> acompanhamento, Optional<Sobremesa>  sobremesa) {
        this.optionalLanche = lanche;
        this.optionalBebida = bebida;
        this.optionalAcompanhamento = acompanhamento;
        this.optionalSobremesa = sobremesa;
        this.statusPedido = StatusPedido.RECEBIDO;
        this.id = id;
    }

    public Pedido(Optional<Lanche> lanche, Optional<Bebida> bebida, Optional<Acompanhamento> acompanhamento, Optional<Sobremesa>  sobremesa) {
        this.optionalLanche = lanche;
        this.optionalBebida = bebida;
        this.optionalAcompanhamento = acompanhamento;
        this.optionalSobremesa = sobremesa;
        this.statusPedido = StatusPedido.RECEBIDO;
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

    public int getId() {
        return id;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public void setId(int id) {
        this.id = id;
    }
}
