package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.financial;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Item;

import java.util.Optional;

public class SomatoriaItem {
    double valorTotal;

    public SomatoriaItem() {
        this.valorTotal = 0;
    }

    public double adiconarItemASoma(Optional<? extends Item> optionalItem) {
        if (optionalItem.isEmpty()) {
            return this.valorTotal;
        }

        Item item = optionalItem.get();

        this.valorTotal += item.getPreco();
        return valorTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
