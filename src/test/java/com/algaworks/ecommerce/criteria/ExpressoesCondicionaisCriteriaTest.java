package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {
	
	@Test
	public void usarBetween() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.between(
				root.get(Pedido_.dataCriacao),
				LocalDateTime.now().minusDays(2).withSecond(0).withMinute(0).withHour(0),
				LocalDateTime.now()));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.stream()
			.forEach(p -> 
				System.out.println("ID: " + p.getId() + ", Cliente: " + p.getCliente().getNome() + ", Data pedido: " + p.getDataCriacao()));
	}
	
	@Test
	public void usarMaiorMenorComDatas() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.greaterThan(
				root.get(Pedido_.dataCriacao), LocalDateTime.now().minusDays(3)));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.stream()
			.forEach(p -> 
				System.out.println("ID: " + p.getId() + ", Cliente: " + p.getCliente().getNome() + ", Data pedido: " + p.getDataCriacao()));
	}
	
	@Test
	public void usarMaiorMenor() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		//criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Produto_.preco), new BigDecimal(1401)));
		//criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(root.get(Produto_.preco), new BigDecimal(1401)));
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(
						root.get(Produto_.preco), new BigDecimal(300)),
				criteriaBuilder.lessThanOrEqualTo(
						root.get(Produto_.preco), new BigDecimal(2000)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.stream()
			.forEach(p -> System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Valor: " + p.getPreco()));
	}
	
	@Test
	public void usarIsEmpty() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		//criteriaQuery.where(criteriaBuilder.isNotEmpty(root.get(Produto_.categorias)));
		criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Produto_.categorias)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarIsNull() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		//criteriaQuery.where(root.get(Produto_.fotoProduto).isNull());
		//criteriaQuery.where(root.get(Produto_.fotoProduto).isNotNull());
		criteriaQuery.where(criteriaBuilder.isNull(root.get(Produto_.fotoProduto)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressaoCondicionalLike() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.like(root.get(Cliente_.nome), "%a%"));
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Cliente> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
}
