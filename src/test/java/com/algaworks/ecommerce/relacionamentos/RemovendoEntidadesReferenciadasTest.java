package com.algaworks.ecommerce.relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class RemovendoEntidadesReferenciadasTest extends EntityManagerTest {
	
	@Test
	public void removerEntidadeReferenciada() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		Assert.assertFalse(pedido.getItensPedido().isEmpty());
		
		entityManager.getTransaction().begin();
		pedido.getItensPedido().forEach(i -> entityManager.remove(i));
		entityManager.remove(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNull(pedidoVerificacao);
	}
	
}
