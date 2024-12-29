package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.PersistenceUsuarioAdapter;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioService {
    private PersistenceUsuarioAdapter persistenceUsuarioAdapter;
    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioService usuarioService;

    public UsuarioService(PersistenceUsuarioAdapter persistenceUsuarioAdapter, Encriptador encriptador) {
        this.persistenceUsuarioAdapter = persistenceUsuarioAdapter;
        this.usuarioService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioService(encriptador);
    }

    public Usuario criar(String email, String senha) throws Exception {
        String senhaForte = usuarioService.encriptarTexto(senha);
        Usuario usuario = new Usuario(email, senhaForte);

        return persistenceUsuarioAdapter.salvar(usuario);

    }

    public UserDetails logar(String email, String senha) throws Exception {
        UserDetails userDetails = buscarUsuario(email);

        boolean isSamePassword = usuarioService.senhasBatem(senha, userDetails.getPassword());

        if(isSamePassword){
            return userDetails;
        }

        throw new Exception("Usuário ou senha incorretos");
    }

    public UserDetails buscarUsuario(String email) throws Exception {
        Usuario user = persistenceUsuarioAdapter.buscarPorEmail(email).orElseThrow(
                () -> new Exception("Usuário com esse nome não existe"));

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .build();
    }
}
