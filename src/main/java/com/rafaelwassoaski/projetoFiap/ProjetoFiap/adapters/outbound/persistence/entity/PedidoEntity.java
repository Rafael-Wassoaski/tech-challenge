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
    @OneToOne
    @JoinColumn(name = "id")
    private LancheEntity lanche;
    @OneToOne
    @JoinColumn(name = "id")
    private BebidaEntity bebida;
    @OneToOne
    @JoinColumn(name = "id")
    private AcompanhamentoEntity acompanhamento;
    @OneToOne
    @JoinColumn(name = "id")
    private SobremesaEntity sobremesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id", nullable = true)
    private UsuarioEntity usuario;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

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
