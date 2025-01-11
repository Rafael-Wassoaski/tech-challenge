package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.financial.SomatoriaItem;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.*;

import java.util.Optional;

public class PedidoService {

    public boolean nomeDoItemEhVazio(String nomeItem) {
        return nomeItem == null || nomeItem.isEmpty();
    }

    public double getTotal(Pedido pedido) {
        SomatoriaItem somatoriaItem = new SomatoriaItem();

        somatoriaItem.adiconarItemASoma(pedido.getLanche());
        somatoriaItem.adiconarItemASoma(pedido.getAcompanhamento());
        somatoriaItem.adiconarItemASoma(pedido.getBebida());
        somatoriaItem.adiconarItemASoma(pedido.getSobremesa());

        return somatoriaItem.getValorTotal();
    }

    public boolean clientePodeVerPedido(Pedido pedido, Usuario usuario){
        Usuario usuarioPedido = pedido.getUsuario();
        String emailUsuarioSolicitante = usuario.getEmail();

        return usuarioPedido == null || emailUsuarioSolicitante.equals(usuarioPedido.getEmail());
    }
}
