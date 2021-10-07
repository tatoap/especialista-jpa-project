package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class BasicoCriteriaTest extends EntityManagerTest {
	
	@Test
	public void projetarOResultadoTuple() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(criteriaBuilder
				.tuple(root.get("id").alias("id"), root.get("nome").alias("nome")));
		
		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Tuple> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(t -> System.out.println("ID: " + t.get("id") + ", Nome: " + t.get("nome")));
	}
	
	@Test
	public void projetarOResultado() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.multiselect(root.get("id"), root.get("nome"));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println("ID " + arr[0] + ", Nome:" + arr[1]));
	}
	
	@Test
	public void retornarTodosOsProdutos() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void selecionarUmAtributoParaRetorno() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		//CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		//criteriaQuery.select(root.get("cliente")); // a mesma coisa que "select p.cliente from Pedido p where p.id = 1"
		criteriaQuery.select(root.get("total"));
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		
		//TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);
		
		//Cliente cliente = typedQuery.getSingleResult();
		BigDecimal total = typedQuery.getSingleResult();
		
		//Assert.assertEquals("Renato Ramos", cliente.getNome());
		Assert.assertEquals(new BigDecimal("2398.00"), total);
	}
	
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
