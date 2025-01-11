package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioDomainService;

public class LancheService extends ItemUseCase<Lanche> {
    public LancheService(PersistenceItemRepository<Lanche> persistenceItemRepository) {
        super(persistenceItemRepository);
    }

    public Lanche criar(Lanche item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode criar este item");
        }
        return super.criar(item);
    }

    public Lanche atualizar(Lanche item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode atualizar este item");
        }
        return super.atualizar(item);
    }

    public void deletarPorNome(String nomeLanche, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode deletar este item");
        }

        super.deletarPorNome(nomeLanche);
    }
}
