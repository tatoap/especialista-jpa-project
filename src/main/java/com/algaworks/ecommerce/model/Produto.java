package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algaworks.ecommerce.listener.GenericoListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "produto")
@EntityListeners({ GenericoListener.class })
public class Produto extends EntidadeBaseInteger {
	
	@Column(name = "data_criacao", updatable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_ultima_atualizacao", insertable = false)
	private LocalDateTime dataUltimaAtualizacao;

	private String nome;

	private String descricao;

	private BigDecimal preco;
	
	@Lob
	private byte[] fotoProduto;
	
	@OneToOne(mappedBy = "produto")
	private Estoque estoque;
	
	@ManyToMany
	@JoinTable(name = "produto_categoria", 
			joinColumns = @JoinColumn(name = "produto_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias;
	
	@ElementCollection // para que o JPA gerencie essa propriedade
	@CollectionTable(name = "produto_tag", // ele cria uma outra tabela para essa propriedade
			joinColumns = @JoinColumn(name = "produto_id"))
	@Column(name = "tag")
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "produto_atributo",
			joinColumns = @JoinColumn(name = "produto_id"))
	private List<Atributo> atributos;

}
