package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

import java.util.*;

public class MapPersistenceUsuarioForTests implements PersistenceUsuarioRepository {

    private Map<String, Usuario> usuarios;

    public MapPersistenceUsuarioForTests() {
        this.usuarios = new HashMap<>();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        this.usuarios.put(usuario.getEmail(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String nome) {
        Usuario usuario = this.usuarios.get(nome);
        return Optional.of(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        Usuario usuario = this.usuarios.get(cpf);
        return Optional.of(usuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return new ArrayList<Usuario>(usuarios.values());
    }

    @Override
    public void deletarPorEmail(String nome) {
        this.usuarios.remove(nome);
    }

    @Override
    public Usuario atualizar(Usuario itemSalvo) {
        this.usuarios.put(itemSalvo.getEmail(), itemSalvo);
        return this.usuarios.get(itemSalvo.getEmail());
    }
}
