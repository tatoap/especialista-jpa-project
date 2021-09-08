package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido")
@EntityListeners({ GerarNotaFiscalListener.class, GenericoListener.class })
public class Pedido extends EntidadeBaseInteger {
	
	@ManyToOne(optional = false) // por padrão é "true", o que é menos performatico pois a relação é left outer join, como "false" é inner join, deve ser alterado para todos os atributos que são obrigatórios para persistência
	@JoinColumn(name = "cliente_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
	private Cliente cliente;
	
	//@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER) // por padrão, o comportamento para busca de lista é Lazy
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.REFRESH)
	private List<ItemPedido> itensPedido;
	
	@Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    
    @OneToOne(mappedBy = "pedido")
	private NotaFiscal notaFiscal;
	
    @Column(nullable = false)
	private BigDecimal total;
	
    @Column(length = 11, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	@OneToOne(mappedBy = "pedido")
	private Pagamento pagamento;
	
	@Embedded
	private EnderecoPedidoEntrega enderecoPedido;
	
	public boolean isPago() {
		return StatusPedido.PAGO.equals(status);
	}
	
	//@PrePersist
	//@PreUpdate
	public void calcularTotal() {
		if (itensPedido != null) {
			total = itensPedido.stream().map(ItemPedido::getPrecoProduto)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
	}
	
	@PrePersist
	public void aoPersistir() {
		dataCriacao = LocalDateTime.now();
		calcularTotal();
	}
	
	@PreUpdate
	public void aoAtualizar() {
		dataUltimaAtualizacao = LocalDateTime.now();
		calcularTotal();
	}
	
	@PostPersist
	public void aposPersistir() {
		System.out.println("Após persistir pedido.");
	}
	
	@PostUpdate
	public void aposAtualizar() {
		System.out.println("Após atualizar pedido");
	}
	
	@PreRemove
	public void aoRemover() {
		System.out.println("Antes de remover pedido.");
	}
	
	@PostRemove
	public void aposRemover() {
		System.out.println("Após remover pedido.");
	}
	
	@PostLoad
	public void aoCarregar() {
		System.out.println("Após carregar o pedido.");
	}
}
