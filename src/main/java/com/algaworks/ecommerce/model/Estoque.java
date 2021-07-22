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
public class Estoque {
	
	@Id
	@EqualsAndHashCode.Include
	private Integer id;
	
	private Integer produtoId;
	
	private Integer quantidade;

}
