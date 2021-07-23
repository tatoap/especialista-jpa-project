package com.algaworks.ecommerce.iniciandocomjpa;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;

public class PrimeiroCrudTest extends EntityManagerTest {
	
	@Test
	public void buscarPorIdentificador() {
		Cliente clienteVerificacao = entityManager.find(Cliente.class, 1);
		
		Assert.assertNotNull(clienteVerificacao);
	}
	
	@Test
	public void inserirObjeto() {
		Cliente cliente = new Cliente();
		
		//cliente.setId(3); foi comentado porque estamos usando a estratégia IDENTITY
		cliente.setNome("Henrique Ramos");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao);
	}
	
	@Test
	public void alterarObjeto() {
		Cliente clienteMerge = entityManager.find(Cliente.class, 1);
		
		//clienteMerge.setId(1); foi comentado porque estamos usando a estratégia IDENTITY
		clienteMerge.setNome("Eliana Rodrigues");
		
		entityManager.getTransaction().begin();
		entityManager.merge(clienteMerge);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, clienteMerge.getId());
		Assert.assertEquals("Eliana Rodrigues", clienteVerificacao.getNome());
	}
	
	@Test
	public void excluirObjeto() {
		Cliente cliente = entityManager.find(Cliente.class, 2);
		
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, 2);
		Assert.assertNull(clienteVerificacao);
	}

}
