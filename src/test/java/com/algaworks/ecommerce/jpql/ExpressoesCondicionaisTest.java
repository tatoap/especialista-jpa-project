package com.algaworks.ecommerce.jpql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class ExpressoesCondicionaisTest extends EntityManagerTest {
	
	@Test
	public void usarExpressaoDiferente() {
		String jpql = "select p from Produto p where p.preco <> 100";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarBetween() {
		String jpql = "select p from Pedido p where p.dataCriacao between :dataInicial and :dataFinal";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(2));
		typedQuery.setParameter("dataFinal", LocalDateTime.now());
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarMaiorEMenorComDatas() {
		String jpql = "select p from Pedido p where p.dataCriacao >= :dataInicial "
				+ "and p.dataCriacao <= :dataFinal";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(2));
		typedQuery.setParameter("dataFinal", LocalDateTime.now());
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarMaiorEMenor() {
		String jpql = "select p from Produto p where p.preco >= :precoInicial "
				+ "and p.preco <= :precoFinal";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		typedQuery.setParameter("precoInicial", new BigDecimal(400));
		typedQuery.setParameter("precoFinal", new BigDecimal(1500));
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarIsNull() {
		String jpql = "select p from Produto p where p.fotoProduto is null";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarIsEmpty() {
		String jpql = "select p from Produto p where p.categorias is empty";
		//String jpql = "select p from Produto p where p.categorias is not empty";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressaoCondicionalLike() {
		String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("nome", "Ramos");
		List<Cliente> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
}