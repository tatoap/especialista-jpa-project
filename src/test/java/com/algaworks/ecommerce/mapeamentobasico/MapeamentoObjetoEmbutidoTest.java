package com.algaworks.ecommerce.mapeamentobasico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.EnderecoPedidoEntrega;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest {
	
	@Test
	public void analisarMapeamentoObjetoEmbutido() {
		Pedido pedido = new Pedido();
		
		EnderecoPedidoEntrega enderecoPedido = new EnderecoPedidoEntrega();
		enderecoPedido.setCep("00000-000");
		enderecoPedido.setLogradouro("Rua das Laranjeiras");
		enderecoPedido.setNumero("2A");
		enderecoPedido.setBairro("Funcion�rios");
		enderecoPedido.setCidade("Belo Horizonte");
		enderecoPedido.setEstado("MG");
		
		pedido.setId(1);
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(new BigDecimal(50));
		pedido.setEnderecoPedido(enderecoPedido);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao);
		Assert.assertNotNull(pedidoVerificacao.getEnderecoPedido());
		Assert.assertEquals("Funcion�rios", pedidoVerificacao.getEnderecoPedido().getBairro());
	}

}