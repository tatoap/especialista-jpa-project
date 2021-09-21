package com.algaworks.ecommerce.jpql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
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
	public void usarExpressaoIN() {
		Cliente cliente1 = entityManager.find(Cliente.class, 1);
		Cliente cliente2 = new Cliente();
		cliente2.setId(2);
		
		//List<Integer> parametros = Arrays.asList(1, 3, 4);
		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		
		//String jpql = "select p from Pedido p where p.id in (:lista)";
		String jpql = "select p from Pedido p where p.cliente in (:lista)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		//typedQuery.setParameter("lista", parametros);
		typedQuery.setParameter("lista", clientes);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarExpressaoCase() {
		/*String jpql = "select p.id, "
				+ "case p.status "
				+ "when 'PAGO' then 'Est� pago' "
				+ "when 'CANCELADO' then 'Foi cancelado' "
				+ "else 'Est� aguardando' "
				+ "end "
				+ "from Pedido p";*/
		
		String jpql = "select p.id, "
				+ "case type(p.pagamento) "
				+ "when PagamentoBoleto then 'Pago com boleto' "
				+ "when PagamentoCartao then 'Pago com cart�o' "
				+ "else 'N�o consta pagamento' "
				+ "end "
				+ "from Pedido p";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println(p[0] + ", " + p[1]));
	}
	
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
		//String jpql = "select p from Produto p where p.categorias is empty";
		String jpql = "select p from Produto p where p.categorias is not empty";
		
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
