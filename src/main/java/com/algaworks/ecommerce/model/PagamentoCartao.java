package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
	
	@Id
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "pedido_id")
	private Integer pedidoId;
	
	@Enumerated(EnumType.STRING)
	private StatusPagamento status;
	
	@Column(name = "numero_cartao")
	private String numeroCartao;

}
