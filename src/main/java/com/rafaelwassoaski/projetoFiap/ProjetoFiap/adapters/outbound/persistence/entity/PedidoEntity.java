package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.StatusPedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Pedido;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @OneToOne
    @JoinColumn(name = "id")
    private UsuarioEntity usuario;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    public PedidoEntity(Pedido pedido){
        this.statusPedido = pedido.getStatusPedido();
        this.usuario = new UsuarioEntity(pedido.getUsuario());

        if(pedido.getLanche().isPresent()){
            this.lanche = new LancheEntity(pedido.getLanche().get());
        }

        if(pedido.getBebida().isPresent()){
            this.bebida = new BebidaEntity(pedido.getBebida().get());
        }

        if(pedido.getAcompanhamento().isPresent()){
            this.acompanhamento = new AcompanhamentoEntity(pedido.getAcompanhamento().get());
        }
        if(pedido.getSobremesa().isPresent()){
            this.sobremesa = new SobremesaEntity(pedido.getSobremesa().get());
        }
    }

    public Pedido converterParaPedido(){
        return new Pedido(id,
                Optional.of(lanche.converterParaLanche()),
                Optional.of(bebida.converterParaBebida()),
                Optional.of(acompanhamento.converterParaAcompanhamento()),
                Optional.of(sobremesa.converterParaSobremesa()),
                usuario.converterParaUsuario(),
                statusPedido
        );
    }

}
