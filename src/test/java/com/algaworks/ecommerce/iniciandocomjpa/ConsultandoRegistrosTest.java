package com.algaworks.ecommerce.iniciandocomjpa;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class ConsultandoRegistrosTest extends EntityManagerTest {
	
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
