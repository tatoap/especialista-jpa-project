package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("cartao")
//@Table(name = "pagamento_cartao")
public class PagamentoCartao extends Pagamento {
	
	@NotEmpty
	@Column(name = "numero_cartao", length = 50)
	private String numeroCartao;

}
