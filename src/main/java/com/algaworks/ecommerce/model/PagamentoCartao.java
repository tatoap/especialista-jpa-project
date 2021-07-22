package com.algaworks.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pagamento_cartao")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PagamentoCartao {
	
	private Integer id;
	
	private Integer pedidoId;
	
	private StatusPagamento status;
	
	private String numeroCartao;

}
