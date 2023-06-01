package br.com.fiap.gs.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.gs.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Page<Usuario> findByNomeContaining(String search, Pageable pageable);

    Usuario findByEmailAndSenha(String email, String senha);

    Optional<Usuario> findByEmail(String email);
}

