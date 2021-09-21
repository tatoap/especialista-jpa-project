package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;

public class SubqueriesTest extends EntityManagerTest {

	@Test
	public void pesquisarSubqueries() {
		//Bons clientes. Vers�o 2.
		String jpql = "select c from Cliente c where "
				+ "500 > (select sum(p.total) from Pedido p where p.cliente = c)";
		
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
