package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;

public class ValidadorItem {

    private Item item;

    public ValidadorItem(Item item) {
        this.item = item;
    }

    public void validar() throws Exception {
        this.validarNome();
        this.validarValor();
    }

    private void validarNome() throws Exception {
        if(this.item.getNome() == null || this.item.getNome().isEmpty()){
            throw new Exception("O nome informado não é válido");
        }
    }

    private void validarValor() throws Exception {
        if(this.item.getPreco() == 0){
            throw new Exception("O valor informado não é pode ser zero");
        }
    }
}
