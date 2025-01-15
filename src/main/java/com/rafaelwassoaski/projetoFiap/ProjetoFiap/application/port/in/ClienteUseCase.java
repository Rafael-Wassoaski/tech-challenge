package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Cliente;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClienteUseCase {

    Cliente criar(Cliente cliente) throws Exception;

    Cliente buscarCliente(String identificador) throws Exception;

    String pegarIdentificador(Cliente cliente);
}
