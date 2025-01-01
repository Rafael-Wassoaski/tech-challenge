package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.BebidaEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.BebidaEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaBebidaRepository extends JpaRepository<BebidaEntity, Integer>, PersistenceItemRepository<Bebida> {
    @Override
    default Bebida atualizar(Bebida itemSalvo) {
        return salvar(itemSalvo);
    }

    @Override
    default void deletarPorNome(String nome) {
        deleteByNome(nome);
    }

    @Override
    default List<Bebida> buscarTodos() {
        List<BebidaEntity> bebidaEntities = findAll();

        return bebidaEntities.stream().map(BebidaEntity::converterParaBebida).collect(Collectors.toList());
    }

    @Override
    default Optional<Bebida> buscarPorNome(String nome) {
        Optional<BebidaEntity> optionalBebidaEntity = findByNome(nome);

        if(optionalBebidaEntity.isEmpty()){
            return Optional.empty();
        }

        BebidaEntity bebidaEntity = optionalBebidaEntity.get();

        return Optional.of(bebidaEntity.converterParaBebida());
    }

    @Override
    default Bebida salvar(Bebida item) {
        BebidaEntity bebidaEntity = new BebidaEntity(item);
        BebidaEntity bebidaSalvo = saveAndFlush(bebidaEntity);

        return bebidaSalvo.converterParaBebida();
    }

    Optional<BebidaEntity> findByNome(String nome);
    void deleteByNome(String nome);

}
