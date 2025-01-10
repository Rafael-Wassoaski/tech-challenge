package com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.port.in.UsuarioUseCase;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
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

    public Usuario criar(String email, String senha) throws Exception {
        String senhaForte = usuarioService.encriptarTexto(senha);
        Usuario usuario = new Usuario(email, senhaForte);

        return persistenceUsuarioRepository.salvar(usuario);
    }

    public UserDetails logar(String email, String senha) throws Exception {
        UserDetails userDetails = buscarUserDetails(email);

        boolean isSamePassword = usuarioService.senhasBatem(senha, userDetails.getPassword());

        if(isSamePassword){
            return userDetails;
        }

        throw new Exception("Usuário ou senha incorretos");
    }

    public UserDetails buscarUserDetails(String email) throws Exception {
        Usuario user = buscarUsuario(email);

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .roles(usuarioService.papeisParaArray(user))
                .build();
    }

    public Usuario buscarUsuario(String email) throws Exception {
        Usuario user = persistenceUsuarioRepository.buscarPorEmail(email).orElseThrow(
                () -> new Exception("Usuário com esse nome não existe"));

        return user;
    }

    @Override
    public boolean usuarioEhGerente(String email) throws Exception {
        Usuario usuario = buscarUsuario(email);

        return Papel.GERENTE.equals(usuario.getPapel());
    }
}
