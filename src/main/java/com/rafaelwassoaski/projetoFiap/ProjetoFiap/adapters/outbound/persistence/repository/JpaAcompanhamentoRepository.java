package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.repository;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.AcompanhamentoEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers.AcompanhamentoMapper;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaAcompanhamentoRepository extends JpaRepository<AcompanhamentoEntity, Integer>, PersistenceItemRepository<Acompanhamento> {
    @Override
    default Acompanhamento atualizar(Acompanhamento itemSalvo) {
        return salvar(itemSalvo);
    }

    @Transactional
    @Override
    default void deletarPorNome(String nome) {
        deleteByNome(nome);
    }

    @Override
    default List<Acompanhamento> buscarTodos() {
        List<AcompanhamentoEntity> acompanhamentoEntities = findAll();

        return acompanhamentoEntities.stream().map(AcompanhamentoMapper::converterParaAcompanhamento).collect(Collectors.toList());
    }

    @Override
    default Optional<Acompanhamento> buscarPorNome(String nome) {
        Optional<AcompanhamentoEntity> optionalAcompanhamentoEntity = findByNome(nome);

        if(optionalAcompanhamentoEntity.isEmpty()){
            return Optional.empty();
        }

        AcompanhamentoEntity acompanhamentoEntity = optionalAcompanhamentoEntity.get();

        return Optional.of(AcompanhamentoMapper.converterParaAcompanhamento(acompanhamentoEntity));
    }

    @Override
    default Acompanhamento salvar(Acompanhamento item) {
        AcompanhamentoEntity acompanhamentoEntity = AcompanhamentoMapper.converterParaAcompanhamentoEntity(item);
        AcompanhamentoEntity acompanhamentoSalvo = saveAndFlush(acompanhamentoEntity);

        return AcompanhamentoMapper.converterParaAcompanhamento(acompanhamentoSalvo);
    }

    Optional<AcompanhamentoEntity> findByNome(String nome);
    void deleteByNome(String nome);

}
