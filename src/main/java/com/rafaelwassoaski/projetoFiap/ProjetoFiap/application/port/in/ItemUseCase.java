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

    public T criar(T item) throws Exception {

        Optional<T> itemSalvo = persistenceItemRepository.buscarPorNome(item.getNome());

        if(itemSalvo.isPresent()){
            throw new Exception("Não é possível criar dois itens com o mesmo nome");
        }


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

    public void deletarPorNome(String nomeItem) throws Exception {
        T itemSalvo = this.buscarPorNome(nomeItem);

        this.persistenceItemRepository.deletarPorNome(itemSalvo.getNome());
    }
}
