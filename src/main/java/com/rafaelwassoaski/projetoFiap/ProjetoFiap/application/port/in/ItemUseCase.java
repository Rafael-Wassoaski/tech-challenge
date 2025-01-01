package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;

import java.util.List;
import java.util.Optional;

public abstract class ItemUseCase {

    private PersistenceItemRepository persistenceItemRepository;

    public ItemUseCase(PersistenceItemRepository persistenceItemRepository) {
        this.persistenceItemRepository = persistenceItemRepository;
    }

    public Item criar(Item item){
        return persistenceItemRepository.salvar(item);
    }

    public Item buscarPorNome(String nome) throws Exception {
        Optional<Item> optionalItem = persistenceItemRepository.buscarPorNome(nome);

        if(optionalItem.isEmpty()){
            throw new Exception(String.format("Item %s não encontrado", nome));
        }

        return optionalItem.get();
    }

    public List<Item> buscarTodosOsItens() {
        return persistenceItemRepository.buscarTodos();
    }

    public Item atualizar(Item item) throws Exception {
        Item itemSalvo = this.buscarPorNome(item.getNome());
        itemSalvo.setPreco(item.getPreco());

        return this.persistenceItemRepository.atualizar(itemSalvo);
    }

    public void deletarPorNome(String nomeLanche) throws Exception {
        Item itemSalvo = this.buscarPorNome(nomeLanche);

        this.persistenceItemRepository.deletarPorNome(itemSalvo.getNome());
        return;
    }
}
