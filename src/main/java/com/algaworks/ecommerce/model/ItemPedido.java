package com.algaworks.ecommerce.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
	
	@Id
	@EqualsAndHashCode.Include
	private Integer id;
	
	private Integer pedidoId;
	
	private Integer produtoId;
	
	private BigDecimal precoProduto;
	
	private Integer quantidade;

}
