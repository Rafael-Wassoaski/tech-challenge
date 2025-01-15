package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.ClienteEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.ClienteEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers.ClienteMapper;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceClienteRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Integer>, PersistenceClienteRepository {
    @Override
    default Cliente atualizar(Cliente cliente) {
        return salvar(cliente);
    }

    @Transactional
    @Override
    default void deletarPorEmail(String email) {
        deleteByEmail(email);
    }

    @Override
    default List<Cliente> buscarTodos() {
        List<ClienteEntity> clienteEntities = findAll();

        return clienteEntities.stream().map(ClienteMapper::converterParaCliente).collect(Collectors.toList());
    }

    @Override
    default Optional<Cliente> buscarPorEmail(String email) {
        Optional<ClienteEntity> optionalClienteEntity = findByEmail(email);
        return extrairCliente(optionalClienteEntity);
    }

    @Override
    default Optional<Cliente> buscarPorCpf(String cpf) {
        Optional<ClienteEntity> optionalClienteEntity = findByCpf(cpf);
        return extrairCliente(optionalClienteEntity);
    }

    default Optional<Cliente> extrairCliente(Optional<ClienteEntity> optionalClienteEntity){
        if (optionalClienteEntity.isEmpty()) {
            return Optional.empty();
        }

        ClienteEntity clienteEntity = optionalClienteEntity.get();
        return Optional.of(ClienteMapper.converterParaCliente(clienteEntity));
    }

    @Override
    default Cliente salvar(Cliente cliente) {
        ClienteEntity clienteEntity = ClienteMapper.converterParaClienteEntity(cliente);

        ClienteEntity clienteEntitySalvo = saveAndFlush(clienteEntity);
        return ClienteMapper.converterParaCliente(clienteEntitySalvo);
    }

    Optional<ClienteEntity> findByEmail(String email);
    Optional<ClienteEntity> findByCpf(String cpf);
    
    public void deleteByEmail(String email);
}
