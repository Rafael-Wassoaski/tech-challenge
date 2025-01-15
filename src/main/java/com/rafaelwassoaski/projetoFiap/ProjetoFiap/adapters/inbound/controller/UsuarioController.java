package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;


import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.dto.TokenDTO;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.JWTService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger log = LogManager.getLogger(UsuarioController.class);
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private Encriptador encriptador;
    private JWTService jwtService;

    public UsuarioController(PersistenceUsuarioRepository persistenceUsuarioRepository, @Value("${custom.sal}") String sal, JWTService jwtService) {
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.encriptador = new Encriptador(sal);
        this.jwtService = jwtService;
    }


    //TODO: Remover esse método depois
    @PostMapping("/cadastro")
    @ResponseStatus( HttpStatus.CREATED)
    public void cadastrar(@RequestBody Usuario usuario) {
        try {
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, encriptador);
            usuarioService.criar(usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao cadastrar o usuario", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public TokenDTO logar(@RequestBody Usuario usuario) {
        try {
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, encriptador);
            UserDetails authenticatedUser = usuarioService.logar(usuario.getEmail(), usuario.getSenha());
            Usuario usuarioLogado = new Usuario();
            usuarioLogado.setSenha(authenticatedUser.getPassword());
            usuarioLogado.setEmail(authenticatedUser.getUsername());

            String token = jwtService.generateToken(usuarioLogado);

            return new TokenDTO(authenticatedUser.getUsername(), token);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao realizar o login", e);

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
