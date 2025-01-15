package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.AcompanhamentoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.AcompanhamentoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security.JWTService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.utils.CookiesUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/acompanhamentos")
public class AcompanhamentoController {
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private static final Logger log = LogManager.getLogger(AcompanhamentoController.class);
    private JWTService jwtService;

    public AcompanhamentoController(PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository, PersistenceUsuarioRepository persistenceUsuarioRepository, JWTService jwtService) {
        this.acompanhamentoPersistenceItemRepository = acompanhamentoPersistenceItemRepository;
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<Acompanhamento> buscarTodosOsAcompanhamentos(){
        AcompanhamentoService acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);

        return acompanhamentoService.buscarTodosOsItens();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void buscarTodosOsAcompanhamentos(@RequestBody Acompanhamento acompanhamento, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            AcompanhamentoService acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);

            acompanhamentoService.criar(acompanhamento, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/atualizar")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarAcompanhamento(@RequestBody Acompanhamento  acompanhamento, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            AcompanhamentoService acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);

            acompanhamentoService.atualizar(acompanhamento, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{nomeAcompanhamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAcompanhamento(@PathVariable String nomeAcompanhamento, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            AcompanhamentoService acompanhamentoService = new AcompanhamentoService(acompanhamentoPersistenceItemRepository);

            acompanhamentoService.deletarPorNome(nomeAcompanhamento, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
