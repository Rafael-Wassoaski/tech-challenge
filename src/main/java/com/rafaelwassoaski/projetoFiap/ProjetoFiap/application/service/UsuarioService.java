package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.UsuarioUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioService implements UsuarioUseCase {
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioService usuarioService;

    public UsuarioService(PersistenceUsuarioRepository persistenceUsuarioRepository, Encriptador encriptador) {
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.usuarioService = new com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioService(encriptador);
    }

    public Usuario criar(String email, String senha) throws Exception {
        String senhaForte = usuarioService.encriptarTexto(senha);
        Usuario usuario = new Usuario(email, senhaForte);

        return persistenceUsuarioRepository.salvar(usuario);

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
        Usuario user = persistenceUsuarioRepository.buscarPorEmail(email).orElseThrow(
                () -> new Exception("Usuário com esse nome não existe"));

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .build();
    }
}
