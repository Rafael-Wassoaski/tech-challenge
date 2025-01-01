package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.LancheEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaLancheRepository extends JpaRepository<LancheEntity, Integer>, PersistenceItemRepository<Lanche> {
    @Override
    default Lanche atualizar(Lanche itemSalvo) {
        return salvar(itemSalvo);
    }

    @Override
    default void deletarPorNome(String nome) {
        deleteByNome(nome);
    }

    @Override
    default List<Lanche> buscarTodos() {
        List<LancheEntity> lancheEntities = findAll();

        return lancheEntities.stream().map(LancheEntity::converterParaLanche).collect(Collectors.toList());
    }

    @Override
    default Optional<Lanche> buscarPorNome(String nome) {
        Optional<LancheEntity> optionalLancheEntity = findByNome(nome);

        if(optionalLancheEntity.isEmpty()){
            return Optional.empty();
        }

        LancheEntity lancheEntity = optionalLancheEntity.get();

        return Optional.of(lancheEntity.converterParaLanche());
    }

    @Override
    default Lanche salvar(Lanche item) {
        LancheEntity lancheEntity = new LancheEntity(item);
        LancheEntity lancheSalvo = saveAndFlush(lancheEntity);

        return lancheSalvo.converterParaLanche();
    }

    Optional<LancheEntity> findByNome(String nome);
    void deleteByNome(String nome);

}
