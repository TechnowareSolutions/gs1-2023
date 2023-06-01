package br.com.fiap.gs.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.fiap.gs.models.Credencial;
import br.com.fiap.gs.models.Token;
import br.com.fiap.gs.models.Usuario;
import br.com.fiap.gs.repository.UsuarioRepository;

@Service
public class TokenService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    public Token generateToken(Credencial credencial) {
        Algorithm alg = Algorithm.HMAC256(secret);
        
        var token = JWT.create()
            .withSubject(credencial.email())
            .withIssuer(issuer)
            .withExpiresAt(Instant.now().plus(29, ChronoUnit.MINUTES))
            .sign(alg);

        return new Token(token, "JWT", "Bearer");
    }

    public Usuario getUserByToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var email = JWT.require(alg)
                        .withIssuer(issuer)
                        .build()
                        .verify(token)
                        .getSubject();
        
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new JWTVerificationException("Usuário não encontrado"));
    }
    
}
