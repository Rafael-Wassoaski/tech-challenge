package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.UsuarioEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers.UsuarioMapper;
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

        return usuarioEntities.stream().map(UsuarioMapper::converterParaUsuario).collect(Collectors.toList());
    }

    @Override
    default Optional<Usuario> buscarPorEmail(String email) {
        Optional<UsuarioEntity> optionalUsuarioEntity = findByEmail(email);
        return extrairUsuario(optionalUsuarioEntity);
    }

    @Override
    default Optional<Usuario> buscarPorCpf(String cpf) {
        Optional<UsuarioEntity> optionalUsuarioEntity = findByCpf(cpf);
        return extrairUsuario(optionalUsuarioEntity);
    }

    default Optional<Usuario> extrairUsuario(Optional<UsuarioEntity> optionalUsuarioEntity){
        if (optionalUsuarioEntity.isEmpty()) {
            Optional.empty();
        }

        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
        return Optional.of(UsuarioMapper.converterParaUsuario(usuarioEntity));
    }

    @Override
    default Usuario salvar(Usuario usuario) {
        UsuarioEntity usuarioEntity = UsuarioMapper.converterParaUsuarioEntity(usuario);

        UsuarioEntity usuarioEntitySalvo = saveAndFlush(usuarioEntity);
        return UsuarioMapper.converterParaUsuario(usuarioEntitySalvo);
    }

    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByCpf(String cpf);
    
    public void deleteByEmail(String email);
}
