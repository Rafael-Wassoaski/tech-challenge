package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.LancheEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers.LancheMapper;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaLancheRepository extends JpaRepository<LancheEntity, Integer>, PersistenceItemRepository<Lanche> {
    @Override
    default Lanche atualizar(Lanche itemSalvo) {
        return salvar(itemSalvo);
    }

    @Transactional
    @Override
    default void deletarPorNome(String nome) {
        deleteByNome(nome);
    }

    @Override
    default List<Lanche> buscarTodos() {
        List<LancheEntity> lancheEntities = findAll();

        return lancheEntities.stream().map(LancheMapper::converterParaLanche).collect(Collectors.toList());
    }

    @Override
    default Optional<Lanche> buscarPorNome(String nome) {
        Optional<LancheEntity> optionalLancheEntity = findByNome(nome);

        if(optionalLancheEntity.isEmpty()){
            return Optional.empty();
        }

        LancheEntity lancheEntity = optionalLancheEntity.get();

        return Optional.of(LancheMapper.converterParaLanche(lancheEntity));
    }

    @Override
    default Lanche salvar(Lanche item) {
        LancheEntity lancheEntity = LancheMapper.converterParaLancheEntity(item);
        LancheEntity lancheSalvo = saveAndFlush(lancheEntity);

        return LancheMapper.converterParaLanche(lancheEntity);
    }

    Optional<LancheEntity> findByNome(String nome);
    void deleteByNome(String nome);

}
