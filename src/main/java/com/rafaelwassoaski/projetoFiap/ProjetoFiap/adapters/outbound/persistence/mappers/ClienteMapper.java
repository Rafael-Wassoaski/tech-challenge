package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.ClienteEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.UsuarioEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

public class ClienteMapper {

    public static ClienteEntity converterParaClienteEntity(Cliente cliente) {
        ClienteEntity clienteEntity = new ClienteEntity();
        if(cliente.getId() != null){
            clienteEntity.setId(cliente.getId());
        }

        clienteEntity.setEmail(cliente.getEmail());
        clienteEntity.setNome(cliente.getNome());
        clienteEntity.setCpf(cliente.getCpf());

        return clienteEntity;
    }

    public static Cliente converterParaCliente(ClienteEntity cliente){
        return new Cliente(cliente.getId(), cliente.getCpf(), cliente.getEmail(), cliente.getNome());
    }
}
