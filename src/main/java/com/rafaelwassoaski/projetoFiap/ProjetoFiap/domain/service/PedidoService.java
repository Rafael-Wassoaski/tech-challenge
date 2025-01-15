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

    public boolean clientePodeVerPedido(Pedido pedido, Cliente cliente){
        Optional<Cliente> clientePedidoOptional = pedido.getCliente();
        ClienteDomainService clienteDomainService = new ClienteDomainService();

        String identificadorClienteSolicitante = clienteDomainService.pegarIdentificador(cliente);

        if (clientePedidoOptional.isPresent()) {
            String identificadorClientePedido = clienteDomainService.pegarIdentificador(clientePedidoOptional.get());
            return identificadorClienteSolicitante.equals(identificadorClientePedido);
        }
        return true;
    }
}
