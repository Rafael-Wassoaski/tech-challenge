package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation.ValidardorEmail;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;

public class UsuarioService {
    private Encriptador encriptador;

    public UsuarioService(Encriptador encriptador) {
        this.encriptador = encriptador;
    }

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
}
