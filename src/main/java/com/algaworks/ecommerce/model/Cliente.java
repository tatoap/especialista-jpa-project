package com.algaworks.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cliente", 
		uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" }) }, // anota uma coluna como unique na tabela
		indexes = { @Index(name = "idx_nome", columnList = "nome") }) // para organizar uma coluna da tabela para buscar de uma forma mais agil
//@EqualsAndHashCode(of = {"id"})
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id")) // permite trazer para uma entidade valores que estão em duas tabelas
public class Cliente extends EntidadeBaseInteger {
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 14, nullable = false)
	private String cpf;
	
	@ElementCollection
	@CollectionTable(name = "cliente_contato",
			joinColumns = @JoinColumn(name = "cliente_id"),
			foreignKey = @ForeignKey(name = "fk_cliente_contato"))
	@MapKeyColumn(name = "tipo")
	@Column(name = "descricao")
	private Map<String, String> contatos;
	
	@Transient // essa marcação faz com que o JPA ignore essa propriedade
	private String primeiroNome;
	
	@Column(table = "cliente_detalhe", length = 9, nullable = false)
	@Enumerated(EnumType.STRING)
	private SexoCliente sexo;
	
	@Column(name = "data_nascimento", table = "cliente_detalhe")
	private LocalDate dataNascimento;

	// esse mapeamento não é necessário. já que na tabela de pedidos esta mapeado
	// como manytoone para cliente
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;
	
	@PostLoad
	public void configurarPrimeiroNome() {
		if (nome != null && !nome.isBlank()) {
			int index = nome.indexOf(" ");
			if (index > -1) {
				this.primeiroNome = nome.substring(0, index);
			}
		}
	}
}
