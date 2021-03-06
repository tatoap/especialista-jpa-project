package com.algaworks.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nota_fiscal")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotaFiscal {
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name = "pedido_id")
	private Integer id;
	
	@NotNull
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido"))
	//@JoinTable(name = "pedido_nota_fiscal", 
	//			joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
	//			inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
	private Pedido pedido;
	
	@NotEmpty
	@Lob
	@Column(nullable = false)
	private byte[] xml;
	
	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_emissao", nullable = false)
	private Date dataEmissao;

}
