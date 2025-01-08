package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;

import java.util.List;
import java.util.Optional;

public abstract class ItemUseCase<T extends Item> {

    private PersistenceItemRepository<T> persistenceItemRepository;

    public ItemUseCase(PersistenceItemRepository<T> persistenceItemRepository) {
        this.persistenceItemRepository = persistenceItemRepository;
    }

    public T criar(T item){
        return persistenceItemRepository.salvar(item);
    }

    public T buscarPorNome(String nome) throws Exception {
        Optional<T> optionalItem = persistenceItemRepository.buscarPorNome(nome);

        if(optionalItem.isEmpty()){
            throw new Exception(String.format("Item %s não encontrado", nome));
        }

        return optionalItem.get();
    }

    public List<T> buscarTodosOsItens() {
        return persistenceItemRepository.buscarTodos();
    }

    public T atualizar(T item) throws Exception {
        T itemSalvo = this.buscarPorNome(item.getNome());
        itemSalvo.setPreco(item.getPreco());

        return this.persistenceItemRepository.atualizar(itemSalvo);
    }

    public void deletarPorNome(String nomeLanche) throws Exception {
        T itemSalvo = this.buscarPorNome(nomeLanche);

        this.persistenceItemRepository.deletarPorNome(itemSalvo.getNome());
    }
}
