package com.algaworks.ecommerce.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("boleto")
//@Table(name = "pagamento_boleto")
public class PagamentoBoleto extends Pagamento {
	
	@NotBlank
	@Column(name = "codigo_barras", length = 100)
	private String codigoBarras;
	
	@NotNull
	@FutureOrPresent
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

}
