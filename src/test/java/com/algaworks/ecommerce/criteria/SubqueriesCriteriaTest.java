package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedido_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

public class SubqueriesCriteriaTest extends EntityManagerTest {
	
	@Test
	public void pesquisarComExists() {
		//todos produtos que j� foram vendidos
		//String jpql = "select p from Produto p where exists " +
		//"(select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));
		
		criteriaQuery.where(criteriaBuilder.exists(subquery));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarComIN() {
		//String jpql = "select p from Pedido p where p.id in " +
		//"(select p2.id from ItemPedido i2 " +
		//"join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> subqueryJoinPedido = subqueryRoot.join(ItemPedido_.pedido);
		Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
		subquery.select(subqueryJoinPedido.get(Pedido_.id));
		subquery.where(criteriaBuilder.greaterThan(subqueryJoinProduto.get(Produto_.preco), new BigDecimal(100)));
		
		criteriaQuery.where(root.get(Pedido_.id).in(subquery));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
		
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
