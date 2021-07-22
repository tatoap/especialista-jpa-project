package com.algaworks.ecommerce.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotaFiscal {
	
	@Id
	@EqualsAndHashCode.Include
	private Integer id;
	
	private Integer pedidoId;
	
	private String xml;
	
	private Date dataEmissao;

}
