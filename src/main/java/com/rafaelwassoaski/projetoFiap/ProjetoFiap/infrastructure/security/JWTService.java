package com.rafaelwassoaski.projetoFiap.ProjetoFiap.infrastructure.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;


@Service
public class JWTService {

    @Value("${security.jwt.expiration}")
    private String horasParaExpiracao;

    @Value("${security.jwt.signatureKey}")
    private String assinaturaDoToken;

    public String generateToken (Usuario usuario){
        long expirationTime = Long.parseLong(horasParaExpiracao);
        LocalDateTime timeOfExpiration = LocalDateTime.now().plusHours(expirationTime);
        Instant instanteOfExpiration = timeOfExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(instanteOfExpiration);

        return Jwts
                .builder()
                .setSubject(usuario.getEmail())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, assinaturaDoToken)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getPublicKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(assinaturaDoToken));
    }

    public String getUsername(String token){
        Claims claims = this.getClaims(token);

        return claims.getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Claims claims = this.getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime expirationTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(expirationTime);
        }catch(Exception exception){
            return false;
        }
    }
}