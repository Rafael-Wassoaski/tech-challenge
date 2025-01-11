package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioDomainService;

public class AcompanhamentoService extends ItemUseCase<Acompanhamento> {
    public AcompanhamentoService(PersistenceItemRepository<Acompanhamento> persistenceItemRepository) {
        super(persistenceItemRepository);
    }

    public Acompanhamento criar(Acompanhamento item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode criar este item");
        }
        return super.criar(item);
    }

    public Acompanhamento atualizar(Acompanhamento item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode atualizar este item");
        }
        return super.atualizar(item);
    }

    public void deletarPorNome(String nomeAcompanhamento, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode deletar este item");
        }

        super.deletarPorNome(nomeAcompanhamento);
    }
}
