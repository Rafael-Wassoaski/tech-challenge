package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioDomainService;

public class BebidaService extends ItemUseCase<Bebida> {
    public BebidaService(PersistenceItemRepository<Bebida> persistenceItemRepository) {
        super(persistenceItemRepository);
    }

    public Bebida criar(Bebida item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode criar este item");
        }
        return super.criar(item);
    }

    public Bebida atualizar(Bebida item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode atualizar este item");
        }
        return super.atualizar(item);
    }

    public void deletarPorNome(String nomeBebida, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode deletar este item");
        }

        super.deletarPorNome(nomeBebida);
    }
}
