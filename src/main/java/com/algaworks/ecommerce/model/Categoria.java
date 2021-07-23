package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categoria")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tabela")
	@TableGenerator(name = "tabela", table = "hibernate_sequences", 
					pkColumnName = "sequence_name",
					pkColumnValue = "categoria",
					valueColumnName = "next_val",
					initialValue = 0,
					allocationSize = 50)
	private Integer id;
	
	private String nome;
	
	@Column(name = "categoria_pai_id")
	private Integer categoriaPaiId;
}
