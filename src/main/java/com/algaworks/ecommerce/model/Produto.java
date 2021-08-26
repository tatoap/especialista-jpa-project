package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algaworks.ecommerce.listener.GenericoListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "produto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({ GenericoListener.class })
public class Produto {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private String descricao;

	private BigDecimal preco;
	
	@OneToOne(mappedBy = "produto")
	private Estoque estoque;
	
	@ManyToMany
	@JoinTable(name = "produto_categoria", 
			joinColumns = @JoinColumn(name = "produto_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias;

}
