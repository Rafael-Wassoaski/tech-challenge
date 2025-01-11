package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidardorEmail;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;

public class UsuarioDomainService {
    private Encriptador encriptador;

    public UsuarioDomainService(Encriptador encriptador) {
        this.encriptador = encriptador;
    }

    public UsuarioDomainService() {}

    public void validarEmail(Usuario usuario) throws Exception {
        ValidardorEmail validardorEmail = new ValidardorEmail();

        if(!validardorEmail.validarEmail(usuario.getEmail())){
            throw new Exception("O e-mail não é válido");
        }
    }

    public boolean senhasBatem(String senhaOriginal, String senhaCriptografada){
        return encriptador.senhasBatem(senhaOriginal, senhaCriptografada);
    }

    public String encriptarTexto(String senha){
        return encriptador.encriptarTexto(senha);
    }

    public String[] papeisParaArray(Usuario usuario){
        return new String[]{usuario.getPapel().name()};
    }

    public boolean usuarioEhGerente(Usuario usuario) {
        return Papel.GERENTE.equals(usuario.getPapel());
    }

}
