package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;

public class FuncoesTest extends EntityManagerTest {
	
	@Test
	public void aplicarFuncoesAgregacao() {
		// avg, count, min, max, sum
		
		String jpql = "select sum(p.total) from Pedido p";
		
		TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);
		List<Number> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println(obj));
	}
	
	@Test
	public void aplicarFuncoesNativas() {
		// dayname - retorna o nome da data passada
		
		String jpql = "select function('dayname', p.dataCriacao) from Pedido p "
				+ "where function('acima_media_faturamento', p.total) = 1";
		
		TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
		List<String> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println(obj));
	}
	
	@Test
	public void aplicarFuncoesColecao() {
		String jpql = "select size(p.itensPedido) from Pedido p where size(p.itensPedido) > 1";
		
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
		List<Integer> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(size -> System.out.println(size));
	}
	
	@Test
	public void aplicarFuncoesNumero() {
		// abs - retorna valor absoluto
		// mod - retorna resto da divis?o
		// sqrt - retorna raiz quadrada
		
		String jpql = "select abs(p.total), mod(p.id, 2), sqrt(p.total) from Pedido p "
				+ "where abs(p.total) > 1000";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
	}
	
	@Test
	public void aplicarFuncoesData() {
		// TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		//current_date, current_time, current_timestamp
		// year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao)
		
		String jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) "
				+ "from Pedido p where hour(p.dataCriacao) < 18";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
	}
	
	@Test
	public void aplicarFuncoesString() {
		// concat, length, locate, substring, lower, upper, trim
		
		String jpql = "select c.nome, length(c.nome) from Categoria c "
				+ "where substring(c.nome, 1, 1) = 'N'";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(c -> System.out.println(c[0] + " - " + c[1]));
	}

}
