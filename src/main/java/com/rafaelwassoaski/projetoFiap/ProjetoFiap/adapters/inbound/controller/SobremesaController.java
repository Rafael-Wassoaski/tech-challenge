package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.SobremesaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.SobremesaService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
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
@RequestMapping("/sobremesas")
public class SobremesaController {
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    private PersistenceUsuarioRepository persistenceUsuarioRepository;
    private static final Logger log = LogManager.getLogger(SobremesaController.class);
    private JWTService jwtService;

    public SobremesaController(PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository, PersistenceUsuarioRepository persistenceUsuarioRepository, JWTService jwtService) {
        this.sobremesaPersistenceItemRepository = sobremesaPersistenceItemRepository;
        this.persistenceUsuarioRepository = persistenceUsuarioRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<Sobremesa> buscarTodasAsSobremesas(){
        SobremesaService sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);

        return sobremesaService.buscarTodosOsItens();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void buscarTodosOsSobremesas(@RequestBody Sobremesa sobremesa, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String cpf = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(cpf);
            SobremesaService sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);

            sobremesaService.criar(sobremesa, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/atualizar")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarSobremesa(@RequestBody Sobremesa  sobremesa, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String cpf = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(cpf);
            SobremesaService sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);

            sobremesaService.atualizar(sobremesa, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{nomeSobremesa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarSobremesa(@PathVariable String nomeSobremesa, HttpServletRequest request){
        try {
            String token = CookiesUtils.extractTokenCookie(request).get();
            String cpf = jwtService.getUsername(token);
            UsuarioService usuarioService = new UsuarioService(persistenceUsuarioRepository);
            Usuario usuario = usuarioService.buscarUsuario(cpf);
            SobremesaService sobremesaService = new SobremesaService(sobremesaPersistenceItemRepository);

            sobremesaService.deletarPorNome(nomeSobremesa, usuario);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o pedido", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
