package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;

public class FuncoesStringTest extends EntityManagerTest {
	
	@Test
	public void aplicarFuncoes() {
		String jpql = "select c.nome, length(c.nome) from Categoria c "
				+ "where substring(c.nome, 1, 1) = 'N'";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(c -> System.out.println(c[0] + " - " + c[1]));
	}

}
