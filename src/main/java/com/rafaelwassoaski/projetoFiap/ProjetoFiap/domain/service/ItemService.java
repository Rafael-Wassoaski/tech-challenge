package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;


import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidadorItem;

public abstract class ItemService {

    public ItemService() {
    }

    public void validar(Item item) throws Exception {
        ValidadorItem validadorItem = new ValidadorItem(item);
        validadorItem.validar();
    }
}
