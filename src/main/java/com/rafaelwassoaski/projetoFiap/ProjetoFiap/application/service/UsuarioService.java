package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.UsuarioUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.service.UsuarioDomainService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioService implements UsuarioUseCase {
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private UsuarioDomainService usuarioService;

    public UsuarioService(PersistenceUsuarioRepository persistenceUsuarioRepository, Encriptador encriptador) {
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.usuarioService = new UsuarioDomainService(encriptador);
    }

    public UsuarioService(PersistenceUsuarioRepository persistenceUsuarioRepository) {
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
    }

    public Usuario criar(Usuario usuarioDTO) throws Exception {
        String senha = usuarioDTO.getSenha();
        String email = usuarioDTO.getEmail();
        String senhaForte = tornarSenhaForte(senha);

        Usuario usuario = new Usuario(email, senhaForte);

        return persistenceUsuarioRepository.salvar(usuario);
    }

    private String tornarSenhaForte(String senha) {
        if (senha != null) {
            return usuarioService.encriptarTexto(senha);
        }

        return null;
    }

    public UserDetails logar(String cpf, String senha) throws Exception {
        UserDetails userDetails = buscarUserDetails(cpf);

        boolean isSamePassword = usuarioService.senhasBatem(senha, userDetails.getPassword());

        if (isSamePassword) {
            return userDetails;
        }

        throw new Exception("Usuário ou senha incorretos");
    }

    public UserDetails buscarUserDetails(String cpf) throws Exception {
        Usuario user = buscarUsuario(cpf);

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .roles(usuarioService.papeisParaArray(user))
                .build();
    }

    public Usuario buscarUsuario(String email) throws Exception {
        Usuario user = persistenceUsuarioRepository.buscarPorEmail(email).orElseThrow(
                () -> new Exception("Usuário com esse cpf não existe"));

        return user;
    }
}
