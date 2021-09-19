package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;

public class PathExpressionTest extends EntityManagerTest {
	
	@Test
	public void buscarPedidoComProdutoEspecifico() {
		//String jpql = "select p from Pedido p join p.itensPedido i where i.id.produtoId = 1";
		String jpql = "select p from Pedido p join p.itensPedido i where i.produto.id = 1";
		//String jpql = "select p from Pedido p join p.itensPedido i join i.produto pro where pro.id = 1";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void usarPathExpressions() {
		String jpql = "select p.cliente.nome from Pedido p";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
}
