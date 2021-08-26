package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(optional = false) // por padrão é "true", o que é menos performatico pois a relação é left outer join, como "false" é inner join, deve ser alterado para todos os atributos que são obrigatórios para persistência
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	//@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER) // por padrão, o comportamento para busca de lista é Lazy
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itensPedido;
	
	@Embedded
	private EnderecoPedidoEntrega enderecoPedido;
	
	@Column(name = "data_pedido")
	private LocalDateTime dataPedido;
	
	@Column(name = "data_conclusao")
	private LocalDateTime dataConclusao;
	
	@OneToOne(mappedBy = "pedido")
	private NotaFiscal notaFiscal;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	@OneToOne(mappedBy = "pedido")
	private PagamentoCartao pagamento;
	
	private BigDecimal total;

}
