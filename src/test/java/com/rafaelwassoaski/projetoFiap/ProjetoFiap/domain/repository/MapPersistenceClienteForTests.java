package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;

import java.util.*;

public class MapPersistenceClienteForTests implements PersistenceClienteRepository {

    private Map<String, Cliente> clienteMap;

    public MapPersistenceClienteForTests() {
        this.clienteMap = new HashMap<>();
    }

    @Override
    public Cliente salvar(Cliente usuario) {
        String identificador = usuario.getCpf() != null ? usuario.getCpf() : usuario.getEmail();

        clienteMap.put(identificador, usuario);
        return usuario;
    }

    @Override
    public Optional<Cliente> buscarPorEmail(String nome) {
        Cliente usuario = clienteMap.get(nome);
        return Optional.of(usuario);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        Cliente usuario = clienteMap.get(cpf);
        return Optional.of(usuario);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return new ArrayList<Cliente>(clienteMap.values());
    }

    @Override
    public void deletarPorEmail(String nome) {
        clienteMap.remove(nome);
    }

    @Override
    public Cliente atualizar(Cliente itemSalvo) {
        clienteMap.put(itemSalvo.getEmail(), itemSalvo);
        return clienteMap.get(itemSalvo.getEmail());
    }
}
