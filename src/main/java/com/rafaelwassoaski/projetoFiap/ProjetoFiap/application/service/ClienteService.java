package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ClienteUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceClienteRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.ClienteDomainService;

import java.util.Optional;

public class ClienteService implements ClienteUseCase {
    private PersistenceClienteRepository persistenceClienteRepository;

    public ClienteService(PersistenceClienteRepository persistenceClienteRepository) {
        this.persistenceClienteRepository = persistenceClienteRepository;
    }

    public Cliente criar(Cliente cliente) throws Exception {
        ClienteDomainService clienteDomainService = new ClienteDomainService();

        String identificador = clienteDomainService.pegarIdentificador(cliente);
        Optional<Cliente> clienteSalvo = buscarClientePorCPFOuEmail(identificador);
        clienteDomainService.clienteEhValido(cliente);

        if(clienteSalvo.isPresent()){
            throw new Exception("Já existe um cliente com este identificador");
        }


        return persistenceClienteRepository.salvar(cliente);
    }

    @Override
    public Cliente buscarCliente(String identificador) throws Exception {
        Optional<Cliente> cliente = buscarClientePorCPFOuEmail(identificador);

        return cliente.orElseThrow(
                () -> new Exception("Cliente não existe"));
    }

    private Optional<Cliente> buscarClientePorCPFOuEmail(String identificador){
        Optional<Cliente> cliente;

        cliente = persistenceClienteRepository.buscarPorCpf(identificador);

        if(cliente.isPresent()){
            return cliente;
        }

        cliente = persistenceClienteRepository.buscarPorEmail(identificador);

        return cliente;

    }
}
