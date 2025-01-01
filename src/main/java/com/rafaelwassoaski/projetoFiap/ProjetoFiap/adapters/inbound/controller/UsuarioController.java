package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;


import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.Encriptador;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RestController("/usuarios")
public class UsuarioController {
    private static final Logger log = LogManager.getLogger(UsuarioController.class);
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private Encriptador encriptador;

    public UsuarioController(PersistenceUsuarioRepository persistenceUsuarioRepository, @Value("${custom.sal}")String sal) {
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.encriptador = new Encriptador(sal);
    }


    @PostMapping("/cadastro")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void cadastrar(Usuario usuario){
        try{
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository, encriptador);
            usuarioService.criar(usuario.getEmail(), usuario.getSenha());
        }catch (Exception e){
            log.error("Ocorreu um erro ao cadastrar o usuario", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
