package com.algaworks.ecommerce.relacionamentos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;

public class RelacionamentoOneToManyTest extends EntityManagerTest {
	
	@Test
	public void verificarRelacionamentoCliente() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		Pedido pedido = new Pedido();
		
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);
		pedido.setCliente(cliente);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertFalse(clienteVerificacao.getPedidos().isEmpty());
	}
	
	@Test
	public void verificarRelacionamentoPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(BigDecimal.TEN);
		pedido.setCliente(cliente);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPrecoProduto(BigDecimal.TEN);
		itemPedido.setQuantidade(2);
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.persist(itemPedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
	}
}
