package br.com.fiap.gs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.gs.config.AuthorizationFilter;
import br.com.fiap.gs.exceptions.RestNotFoundException;
import br.com.fiap.gs.models.Credencial;
import br.com.fiap.gs.models.Usuario;
import br.com.fiap.gs.repository.UsuarioRepository;
import br.com.fiap.gs.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "Usuario", description = "API de usuarios")
@RequestMapping("/api/v1/usuario")
@Slf4j
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PagedResourcesAssembler<Object> assembler; 

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthorizationFilter authorizationFilter;

    // Get User
    @GetMapping
    public ResponseEntity<Usuario> show(HttpServletRequest request){
        Usuario usuario = getUsuario(request);
        log.info("Usuario: {}", usuario);
        log.info("Token: {}", request.getHeader("Authorization"));
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> show(@PathVariable Long id){
        var usuario = getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Usuario> delete(HttpServletRequest request){
        var usuarioOptional = getUsuario(request);
        usuarioRepository.delete(usuarioOptional);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Put
    @PutMapping
    public ResponseEntity<Usuario> update(HttpServletRequest request, @RequestBody @Valid Usuario usuario, BindingResult result){
        Usuario usuarioToken = getUsuario(request);
        log.info("Usuario atualizado com sucesso! " + usuario);
        usuario.setId(usuarioToken.getId());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Credencial credencial){
        authenticationManager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @PostMapping("registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody @Valid Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    private Usuario getUsuario(HttpServletRequest request) {
        var token = authorizationFilter.getToken(request);
        Usuario usuario = tokenService.getUserByToken(token);
        return usuario;
    }

    private Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RestNotFoundException("Usuario não encontrado!"));
    }
}