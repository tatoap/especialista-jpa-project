package com.algaworks.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

	@Id
	@EqualsAndHashCode.Include
	private Integer id;
	
	private String nome;
	
	private Integer categoriaPaiId;
}
