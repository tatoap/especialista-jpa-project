package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.naming.event.ObjectChangeListener;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

public class SubqueriesCriteriaTest extends EntityManagerTest {
	
	/**
	 * 
	 */
	@Test
	public void pesquisarSubqueries03() {
		//bons clientes
		//String jpql = "select c from Cliente c where " +
		//"500 < (select sum(p.total) from Pedido p where p.cliente = c)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)));
		subquery.where(criteriaBuilder.equal(root, subqueryRoot.get(Pedido_.cliente)));
		
		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(500)));
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Cliente> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}
	
	@Test
	public void pesquisarSubqueries02() {
		//todos os pedidos acima da m�dia de vendas
		//String jpql = "select p from Pedido p where " +
		//"p.total > (select avg(total) from Pedido)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class));
		
		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Total: " + obj.getTotal()));
	}
	
	@Test
	public void pesquisarSubqueries01() {
		//o produto ou os produtos mais caros da base
		//String jpql = "select p from Produto p where " +
		//"p.preco = (select max(preco) from Produto)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Produto> subqueryRoot = subquery.from(Produto.class);
		subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.preco)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Pre�o: " + obj.getPreco()));
	}

}
