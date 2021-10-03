package com.algaworks.ecommerce.criteria;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class BasicoCriteriaTest extends EntityManagerTest {
	
	@Test
	public void buscarPorIdentificador() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root); // Opcional, pois o a criteriaQuery já entende que no root estamos querendo fazer um select
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		
		//String jpql = "select p from Pedido p where p.id = 1";
		
		TypedQuery<Pedido> typedQuery = entityManager
				//.createQuery(jpql, Pedido.class);
				.createQuery(criteriaQuery);
		
		Pedido pedido = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido);
	}

}
