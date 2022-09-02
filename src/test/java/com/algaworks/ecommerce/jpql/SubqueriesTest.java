package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class SubqueriesTest extends EntityManagerTest {
	
	//@Test
	public void pesquisarComAny() {
		// Podemos usar o ANY ou o SOME
		
		// Todos produtos que j� foram vendidos por um pre�o diferente do atual
		//String jpql = "select p from Produto p where "
		//		+ "p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";
		
		// Todos produtos que j� foram vendidos pelo menos uma vez com o pre�o atual
		String jpql = "select p from Produto p where "
				+ "p.preco = ANY (select precoProduto from ItemPedido where produto = p)";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID " + obj.getId()));
	}
	
	@Test
	public void pesquisarComAll() {
		// Todos os produtos que sempre foram vendidos com o mesmo pre�o
		String jpql = "select distinct p from ItemPedido ip join ip.produto p where "
				+ "ip.precoProduto = ALL "
				+ "(select precoProduto from ItemPedido where produto = p and id <> ip.id)";
		
		// Todos os produtos n�o foram vendidos mais depois que encareceram
		//String jpql = "select p from Produto p where "
		//		+ "p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
		
		// Todos os produtos que sempre foram vendidos pelo pre�o atual
		//String jpql = "select p from Produto p where "
		//		+ "p.preco = ALL (select precoProduto from ItemPedido where produto = p)";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID " + obj.getId()));
	}
	
	//@Test
	public void pesquisarComExists() {
		//String jpql = "select p from Produto p where exists "
		//		+ "(select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";
		
		//Retorna produtos que n�o foram vendidos com o pre�o atual
		//String jpql = "select p from Produto p where exists "
		//		+ "(select 1 from ItemPedido ip join ip.produto p2 where p2 = p "
		//		+ "and ip.precoProduto <> p.preco)";
		
		//Retorna produtos que n�o foram vendidos com o pre�o atual v2
		String jpql = "select p from Produto p where exists "
				+ "(select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", " + obj.getNome()));
	}
	
	//@Test
	public void pesquisaComIN() {
		/*String jpql = "select p from Pedido p where p.id in "
				+ "(select p2.id from ItemPedido i2 "
				+ "join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";*/
		
		String jpql = "select p from Pedido p where p.id in "
				+ "(select p2.id from ItemPedido i2 "
				+ "join i2.pedido p2 join i2.produto pro2 "
				+ "join pro2.categorias c2 where c2.id = 2)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	//@Test
	public void pesquisarSubqueries() {
		
		String jpql = "select c from Cliente c where "
				+ "(select count(p.id) from Pedido p where p.cliente = c) >= 2";
		
		//Bons clientes. Vers�o 2.
		//String jpql = "select c from Cliente c where "
		//		+ "500 > (select sum(p.total) from Pedido p where p.cliente = c)";
		
		//Bons clientes. Vers�o 1.
		//String jpql = "select c from Cliente c where " +
		//	" 500 < (select sum(p.total) from c.pedidos p)";

		//Todos os pedidos acima da m�dia de vendas
		//String jpql = "select p from Pedido p where " +
		//	" p.total > (select avg(total) from Pedido)";

		//O produto ou os produtos mais caros da base.
		//String jpql = "select p from Produto p where " +
		//	" p.preco = (select max(preco) from Produto)";
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}
	
}
