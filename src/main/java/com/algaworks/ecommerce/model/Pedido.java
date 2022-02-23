package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido")
@EntityListeners({ GerarNotaFiscalListener.class, GenericoListener.class })
public class Pedido extends EntidadeBaseInteger 
	implements PersistentAttributeInterceptable 
	{
	
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY) // por padr�o � "true", o que � menos performatico pois a rela��o � left outer join, como "false" � inner join, deve ser alterado para todos os atributos que s�o obrigat�rios para persist�ncia
	@JoinColumn(name = "cliente_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
	private Cliente cliente;
	
	//@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER) // por padr�o, o comportamento para busca de lista � Lazy
	@NotEmpty
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itensPedido;
	
	@PastOrPresent
	@NotNull
	@Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

	@PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

	@PastOrPresent
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    
	@LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
	private NotaFiscal notaFiscal;
	
    @NotNull
    @Positive
    @Column(nullable = false)
	private BigDecimal total;
	
    @NotNull
    @Column(length = 11, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
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
			total = itensPedido.stream().map(
						i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto()))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		} else {
			total = BigDecimal.ZERO;
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
		System.out.println("Ap�s persistir pedido.");
	}
	
	@PostUpdate
	public void aposAtualizar() {
		System.out.println("Ap�s atualizar pedido");
	}
	
	@PreRemove
	public void aoRemover() {
		System.out.println("Antes de remover pedido.");
	}
	
	@PostRemove
	public void aposRemover() {
		System.out.println("Ap�s remover pedido.");
	}
	
	@PostLoad
	public void aoCarregar() {
		System.out.println("Ap�s carregar o pedido.");
	}
	
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	@Transient
	private PersistentAttributeInterceptor persistentAttributeInterceptor;
	
	public NotaFiscal getNotaFiscal() {
		if (this.persistentAttributeInterceptor != null) {
			return (NotaFiscal) persistentAttributeInterceptor
					.readObject(this, "notaFiscal", this. notaFiscal);
		}

		return this.notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		if (this.persistentAttributeInterceptor != null) {
			this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor
					.writeObject(this, "notaFiscal", this.notaFiscal, notaFiscal);
		} else {
			this.notaFiscal = notaFiscal;
		}
	}

	public Pagamento getPagamento() {
		if (this.persistentAttributeInterceptor != null) {
			return (Pagamento) persistentAttributeInterceptor
					.readObject(this, "pagamento", this.pagamento);
		}

		return this.pagamento;
	}
	
	public void setPagamento(Pagamento pagamento) {
		if (this.persistentAttributeInterceptor != null) {
			this.pagamento = (Pagamento) persistentAttributeInterceptor
					.writeObject(this, "pagamento", this.pagamento, pagamento);
		} else {
			this.pagamento = pagamento;
		}
	}
	
	@Override
	public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
		return this.persistentAttributeInterceptor;
	}

	@Override
	public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
		this.persistentAttributeInterceptor = persistentAttributeInterceptor;
	}
	
}
