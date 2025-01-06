package com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security;

import java.io.IOException;
import java.util.Optional;


import com.rafaelwassoaski.projetoFiap.ProjetoFiap.application.service.UsuarioService;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceUsuarioRepository;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.utils.CookiesUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JWTAuthFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UsuarioService usuarioService;

    public JWTAuthFilter(JWTService jwtService, PersistenceUsuarioRepository persistenceUsuarioRepository, Encriptador encriptador) {
        this.jwtService = jwtService;
        this.usuarioService = new UsuarioService(persistenceUsuarioRepository, encriptador);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> optionalToken = CookiesUtils.extractTokenCookie(request);

        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            boolean isTokenValid = jwtService.isTokenValid(token);

            if (isTokenValid) {
                String username = jwtService.getUsername(token);

                UserDetails customUser = null;
                try {
                    customUser = usuarioService.buscarUserDetails(username);

                } catch (Exception e) {
                    CookiesUtils.removeTokenCookie(response);
                    throw new RuntimeException(e);
                }

                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());

                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}