package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lanche_id")
    private LancheEntity lanche;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bebida_id")
    private BebidaEntity bebida;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "acompanhamento_id")
    private AcompanhamentoEntity acompanhamento;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sobremesa_id")
    private SobremesaEntity sobremesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id", nullable = true)
    private UsuarioEntity usuario;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    public PedidoEntity() {
    }

    public PedidoEntity(int id, LancheEntity lanche, BebidaEntity bebida, AcompanhamentoEntity acompanhamento, SobremesaEntity sobremesa, UsuarioEntity usuario, StatusPedido statusPedido) {
        this.id = id;
        this.lanche = lanche;
        this.bebida = bebida;
        this.acompanhamento = acompanhamento;
        this.sobremesa = sobremesa;
        this.usuario = usuario;
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

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}
