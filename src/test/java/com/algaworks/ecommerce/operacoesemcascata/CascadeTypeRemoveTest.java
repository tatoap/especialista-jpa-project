package com.algaworks.ecommerce.operacoesemcascata;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;

public class CascadeTypeRemoveTest extends EntityManagerTest {
	
	//@Test
	public void removerPedidoEItens() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		entityManager.getTransaction().begin();
		entityManager.remove(pedido); // Necess�rio CascadeType.REMOVE no atributo "itens".
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNull(pedidoVerificacao);
		
	}
	
	//@Test
	public void removerItemPedidoEPedido() {
		ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));
		
		entityManager.getTransaction().begin();
		entityManager.remove(itemPedido); // Necess�rio CascadeType.REMOVE no atributo "pedido".
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
		Assert.assertNull(pedidoVerificacao);
	}
	
}
