package com.algaworks.ecommerce.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@EqualsAndHashCode(of = {"id"})
public class Cliente {
	
	@Id
	//@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@Transient // essa marcação faz com que o JPA ignore esse atributo
	private String primeiroNome;
	
	@Enumerated(EnumType.STRING)
	private SexoCliente sexo;

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
