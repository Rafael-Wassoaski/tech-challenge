package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.ItemUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioDomainService;

public class SobremesaService extends ItemUseCase<Sobremesa> {
    public SobremesaService(PersistenceItemRepository<Sobremesa> persistenceItemRepository) {
        super(persistenceItemRepository);
    }

    public Sobremesa criar(Sobremesa item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode criar este item");
        }
        return super.criar(item);
    }

    public Sobremesa atualizar(Sobremesa item, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode atualizar este item");
        }
        return super.atualizar(item);
    }

    public void deletarPorNome(String nomeSobremesa, Usuario usuario ) throws Exception {
        UsuarioDomainService usuarioDomainService = new UsuarioDomainService();

        if(!usuarioDomainService.usuarioEhGerente(usuario)){
            throw new Exception("Você não pode deletar este item");
        }

        super.deletarPorNome(nomeSobremesa);
    }
}
