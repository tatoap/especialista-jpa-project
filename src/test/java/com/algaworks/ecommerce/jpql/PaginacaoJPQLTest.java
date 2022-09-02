package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;

public class PaginacaoJPQLTest extends EntityManagerTest {
	
	@Test
	public void paginarResultados() {
		String jpql = "select c from Categoria c order by c.nome";
		
		TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql, Categoria.class);
		
		//FIRST_RESULT = MAX_RESULT * (pagina - 1)
		typedQuery.setFirstResult(6);
		typedQuery.setMaxResults(2); // para limitar o resultado da busca, sem o setFirstResult.
		
		List<Categoria> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
	}

}
