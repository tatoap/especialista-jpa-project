package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.algaworks.ecommerce.listener.GenericoListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "produto_loja.Produto",
			entities = {
					@EntityResult(entityClass = Produto.class)
			})
})
@NamedQueries({
	@NamedQuery(name = "Produto.listar", query = "select p from Produto p"),
	@NamedQuery(name = "Produto.listarPorCategoria", query = "select p from Produto p where exists "
			+ "(select 1 from Categoria c2 join c2.produtos p2 where p2 = p and c2.id = :categoria)")
})
@Table(name = "produto",
		uniqueConstraints = { @UniqueConstraint(name = "unq_nome", columnNames = { "nome" }) },
		indexes = { @Index(name = "idx_nome", columnList = "nome") })
@EntityListeners({ GenericoListener.class })
public class Produto extends EntidadeBaseInteger {
	
	@Column(name = "data_criacao", updatable = false, nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_ultima_atualizacao", insertable = false)
	private LocalDateTime dataUltimaAtualizacao;

	@Column(length = 100, nullable = false)
	private String nome;

	@Lob
	private String descricao;

	private BigDecimal preco;
	
	@Lob
	private byte[] fotoProduto;
	
	@OneToOne(mappedBy = "produto")
	private Estoque estoque;
	
	@ManyToMany
	@JoinTable(name = "produto_categoria", 
			joinColumns = @JoinColumn(name = "produto_id", nullable = false,
				foreignKey = @ForeignKey(name = "fk_produto_categoria_produto")),
			inverseJoinColumns = @JoinColumn(name = "categoria_id", nullable = false,
				foreignKey = @ForeignKey(name = "fk_produto_categoria_categoria")))
	private List<Categoria> categorias;
	
	@ElementCollection // para que o JPA gerencie essa propriedade
	@CollectionTable(name = "produto_tag", // ele cria uma outra tabela para essa propriedade
			joinColumns = @JoinColumn(name = "produto_id", nullable = false,
					foreignKey = @ForeignKey(name = "fk_produto_tag_produto")))
	@Column(name = "tag", length = 50, nullable = false)
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "produto_atributo",
			joinColumns = @JoinColumn(name = "produto_id", nullable = false,
					foreignKey = @ForeignKey(name = "fk_produto_atributo_produto")))
	private List<Atributo> atributos;

}
