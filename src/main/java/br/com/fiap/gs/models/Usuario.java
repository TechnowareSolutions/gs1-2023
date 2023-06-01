package br.com.fiap.gs.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name = "T_DAI_USUARIO")
public class Usuario implements UserDetails{

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Long id;

  @NotNull @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
  @Column(name = "nm_usuario")
  private String nome;
  
  @NotNull @Size(min = 3, max = 14, message = "O CPF deve ter entre 3 e 14 caracteres")
  @Column(name = "ds_cpf")
  private String cpf;
  
  @NotNull @Size(min = 3, max = 255, message = "O email deve ter entre 3 e 255 caracteres")
  @Column(name = "ds_email", unique = true)
  private String email;

  @NotNull
  @Column(name = "ds_telefone")
  private String telefone;

  @NotNull
  @Column(name = "dt_nascimento")
  private LocalDate nascimento;

  @NotNull
  @Size(min = 3, max = 255, message = "A senha deve ter entre 3 e 255 caracteres")
  @Column(name = "ds_senha")
  private String senha;

  @NotNull
  @Column(name = "ds_endereco")
  private String endereco;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  } 
}