package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.LancheService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.PedidoService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
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
@RequestMapping("/lanches")
public class LancheController {
    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private static final Logger log = LogManager.getLogger(LancheController.class);
    private JWTService jwtService;



    public LancheController(PersistenceItemRepository<Lanche> lanchePersistenceItemRepository, PersistenceUsuarioRepository persistenceUsuarioRepository, JWTService jwtService) {
        this.lanchePersistenceItemRepository = lanchePersistenceItemRepository;
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<Lanche> buscarTodosOsLanches(){
        LancheService lancheService = new LancheService(lanchePersistenceItemRepository);

        return lancheService.buscarTodosOsItens();

    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Lanche criarLanche(@RequestBody Lanche  lanche, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            LancheService lancheService = new LancheService(lanchePersistenceItemRepository);

            return lancheService.criar(lanche, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o lanche", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/atualizar")
    @ResponseStatus(HttpStatus.OK)
    public Lanche atualizarLanche(@RequestBody Lanche  lanche, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            LancheService lancheService = new LancheService(lanchePersistenceItemRepository);

            return lancheService.atualizar(lanche, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar o lanche", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{nomeLanche}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLanche(@PathVariable String nomeLanche, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String email = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(email);
            LancheService lancheService = new LancheService(lanchePersistenceItemRepository);

            lancheService.deletarPorNome(nomeLanche, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao deletar o lanche", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
