package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import jakarta.persistence.*;

@Entity
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lanche_id")
    private LancheEntity lanche;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bebida_id")
    private BebidaEntity bebida;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acompanhamento_id")
    private AcompanhamentoEntity acompanhamento;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sobremesa_id")
    private SobremesaEntity sobremesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private ClienteEntity cliente;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    public PedidoEntity() {
    }

    public PedidoEntity(int id, LancheEntity lanche, BebidaEntity bebida, AcompanhamentoEntity acompanhamento, SobremesaEntity sobremesa, ClienteEntity cliente, StatusPedido statusPedido) {
        this.id = id;
        this.lanche = lanche;
        this.bebida = bebida;
        this.acompanhamento = acompanhamento;
        this.sobremesa = sobremesa;
        this.cliente = cliente;
        this.statusPedido = statusPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LancheEntity getLanche() {
        return lanche;
    }

    public void setLanche(LancheEntity lanche) {
        this.lanche = lanche;
    }

    public BebidaEntity getBebida() {
        return bebida;
    }

    public void setBebida(BebidaEntity bebida) {
        this.bebida = bebida;
    }

    public AcompanhamentoEntity getAcompanhamento() {
        return acompanhamento;
    }

    public void setAcompanhamento(AcompanhamentoEntity acompanhamento) {
        this.acompanhamento = acompanhamento;
    }

    public SobremesaEntity getSobremesa() {
        return sobremesa;
    }

    public void setSobremesa(SobremesaEntity sobremesa) {
        this.sobremesa = sobremesa;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}
