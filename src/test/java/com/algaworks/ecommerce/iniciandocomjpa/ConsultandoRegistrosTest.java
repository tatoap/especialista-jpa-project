package com.algaworks.ecommerce.iniciandocomjpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Produto;

public class ConsultandoRegistrosTest extends EntityManagerTest {
	
	@Test
	public void selecionarUmAtributoParaRetorno() {
		String jpql = "select p.nome from Produto p";
		
		TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
		List<String> lista = typedQuery.getResultList();
		Assert.assertTrue(String.class.equals(lista.get(0).getClass()));
		
		String jpqlCliente = "select p.cliente from Pedido p";
		
		TypedQuery<Cliente> typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
		List<Cliente> listaCliente = typedQueryCliente.getResultList();
		Assert.assertTrue(Cliente.class.equals(listaCliente.get(0).getClass()));
	}
	
	@Test
	public void buscarPorIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1);
		//Produto produto = entityManager.getReference(Produto.class, 1);
		
		Assert.assertNotNull(produto);
		Assert.assertEquals("Kindle", produto.getNome());
	}
	
	@Test
	public void atualizarReferencia() {
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Microfone Samson");
		
		entityManager.refresh(produto);
		
		Assert.assertEquals("Kindle", produto.getNome());
	}
}
