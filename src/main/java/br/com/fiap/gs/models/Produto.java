package br.com.fiap.gs.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.gs.controllers.ProdutoController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "T_DAI_PRODUTO")
public class Produto {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_produto")
  private Long id;

  @NotNull @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  @NotNull @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
  @Column(name = "nm_produto")
  private String nome;

  @NotNull @Size(min = 3, max = 50, message = "A categoria deve ter entre 3 e 50 caracteres")
  @Column(name = "ds_categoria")
  private String categoria;
  
  @NotNull 
  @Column(name = "dt_validade")
  private LocalDate validade;

  @NotNull
  @Column(name = "ds_cheiro")
  private Integer cheiro;

  @NotNull
  @Column(name = "ds_aparencia")
  private Integer aparencia;

  @NotNull
  @Column(name = "ds_consistencia")
  private Integer consistencia;

  @NotNull
  @Column(name = "ds_embalagem")
  private Integer embalagem;

  @NotNull
  @Column(name = "ds_qualidade")
  private Integer qualidade;
 
  @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
  @Column(name = "ds_produto")
  private String descricao;

  public EntityModel<Produto> toEntityModel(){
    return EntityModel.of(
      this,
      linkTo(methodOn(ProdutoController.class).show(this.getId())).withSelfRel(),
      linkTo(methodOn(ProdutoController.class).delete(this.getId())).withRel("delete"),
      linkTo(methodOn(ProdutoController.class).index(null, Pageable.unpaged())).withRel("all")
    );
  };
}