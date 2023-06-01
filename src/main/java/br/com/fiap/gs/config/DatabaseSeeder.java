package br.com.fiap.gs.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

// import br.com.fiap.gs.models.Produto;
import br.com.fiap.gs.models.Usuario;
// import br.com.fiap.gs.repository.ProdutoRepository;
import br.com.fiap.gs.repository.UsuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{

    // @Autowired
    // private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        // usuarioRepository.saveAll(List.of(
        //     new Usuario (1L, "Usuario 1", "000-000-000.00", "caiogallobarreira@gmail.com", "989759200", LocalDate.now(), "$2a$10$u2U4UZ6hJ/f9CJcvRqNq3OuAEmMandVXQHiW2AE.NI27LlY4hYO7S", "Rua 1")
        // ));
        
    }
    
}
