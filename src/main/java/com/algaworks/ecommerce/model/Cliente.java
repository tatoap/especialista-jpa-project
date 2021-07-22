package com.algaworks.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Cliente {
	
	@Id
	//@EqualsAndHashCode.Include
	private Integer id;
	
	private String nome;

}
