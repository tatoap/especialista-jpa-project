package com.algaworks.ecommerce.jpql;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class BasicoJPQLTest extends EntityManagerTest {

	@Test
	public void buscarPorIdentificador() {
		
		TypedQuery<Pedido> typedQuery = entityManager
				.createQuery("select p from Pedido p where p.id = 1", Pedido.class);
		
		Pedido pedido = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido);
	}
	
	@Test
	public void mostrarDiferencaQueries() {
		String jpql = "select p from Pedido p where p.id = 1";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		Pedido pedido1 = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido1);
		
		Query query = entityManager.createQuery(jpql);
		Pedido pedido2 = (Pedido) query.getSingleResult();
		Assert.assertNotNull(pedido2);
		
		//List<Pedido> lista = query.getResultList();
		//Assert.assertFalse(lista.isEmpty());
	}
	
}
