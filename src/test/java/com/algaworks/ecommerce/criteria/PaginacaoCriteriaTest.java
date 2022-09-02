package com.algaworks.ecommerce.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;

public class PaginacaoCriteriaTest extends EntityManagerTest {

	@Test
	public void paginarResultados() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteriaQuery = criteriaBuilder.createQuery(Categoria.class);
		Root<Categoria> root = criteriaQuery.from(Categoria.class);
		
		criteriaQuery.select(root);
		
		TypedQuery<Categoria> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(4);
		typedQuery.setMaxResults(2);
		
		List<Categoria> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
	}
	
}
