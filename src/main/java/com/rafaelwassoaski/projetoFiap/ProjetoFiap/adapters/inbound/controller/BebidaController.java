package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.BebidaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.LancheService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
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
@RequestMapping("/bebidas")
public class BebidaController {
    private PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository;
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private static final Logger log = LogManager.getLogger(BebidaController.class);
    private JWTService jwtService;

    public BebidaController(PersistenceItemRepository<Bebida> bebidaPersistenceItemRepository, PersistenceUsuarioRepository persistenceUsuarioRepository, JWTService jwtService) {
        this.bebidaPersistenceItemRepository = bebidaPersistenceItemRepository;
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<Bebida> buscarTodasAsBebidas(){
        BebidaService bebidaService = new BebidaService(bebidaPersistenceItemRepository);

        return bebidaService.buscarTodosOsItens();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void buscarTodasAsBebida(@RequestBody Bebida bebida, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            BebidaService bebidaService = new BebidaService(bebidaPersistenceItemRepository);

            bebidaService.criar(bebida, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/atualizar")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarBebida(@RequestBody Bebida bebida, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            BebidaService bebidaService = new BebidaService(bebidaPersistenceItemRepository);

            bebidaService.atualizar(bebida, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{nomeBebida}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarBebida(@PathVariable String nomeBebida, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            BebidaService bebidaService = new BebidaService(bebidaPersistenceItemRepository);

            bebidaService.deletarPorNome(nomeBebida, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
