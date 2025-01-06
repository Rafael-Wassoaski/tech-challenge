package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.UsuarioEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Integer>, PersistenceUsuarioRepository {
    @Override
    default Usuario atualizar(Usuario usuario) {
        return salvar(usuario);
    }

    @Transactional
    @Override
    default void deletarPorEmail(String email) {
        deleteByEmail(email);
    }

    @Override
    default List<Usuario> buscarTodos() {
        List<UsuarioEntity> usuarioEntities = findAll();

        return usuarioEntities.stream().map(usuarioEntity -> usuarioEntity.converterParaUsuario()).collect(Collectors.toList());
    }

    @Override
    default Optional<Usuario> buscarPorEmail(String email) {
        Optional<UsuarioEntity> optionalUsuarioEntity = findByEmail(email);

        if (optionalUsuarioEntity.isEmpty()) {
            Optional.empty();
        }

        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
        return Optional.of(usuarioEntity.converterParaUsuario());
    }

    @Override
    default Usuario salvar(Usuario usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);

        UsuarioEntity usuarioEntitySalvo = saveAndFlush(usuarioEntity);
        return usuarioEntitySalvo.converterParaUsuario();
    }

    Optional<UsuarioEntity> findByEmail(String email);
    public void deleteByEmail(String email);
}
