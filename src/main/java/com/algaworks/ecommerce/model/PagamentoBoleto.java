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
public class PagamentoBoleto {
	
	@Id
	@EqualsAndHashCode.Include
	private Integer id;
	
	private Integer pedidoId;
	
	private StatusPagamento status;
	
	private String codigoBarras;

}
