package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidardorEmail;

public class ClienteDomainService {

    public boolean clienteEhValido(Cliente cliente) throws Exception {
        if(cliente.getEmail() != null){
            validarEmail(cliente.getEmail());
            return true;
        }

        validarCpf(cliente.getCpf());
        return true;
    }

    private void validarEmail(String email) throws Exception {
        ValidardorEmail validardorEmail = new ValidardorEmail();

        if (!validardorEmail.validarEmail(email)) {
            throw new Exception("O e-mail não é válido");
        }
    }

    private void validarCpf(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("Usuário deve ter CPF para ser criado");
        }
    }

    public String pegarIdentificador(Cliente cliente) {
        if(cliente.getCpf() != null){
            return cliente.getCpf();
        }

        return cliente.getEmail();
    }
}
