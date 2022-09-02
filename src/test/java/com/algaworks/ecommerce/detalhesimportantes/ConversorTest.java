package com.algaworks.ecommerce.detalhesimportantes;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class ConversorTest extends EntityManagerTest {
	
	@Test
	public void converter() {
		Produto produto = new Produto();
		produto.setDataCriacao(LocalDateTime.now());
		produto.setNome("Carregador de Notebook Dell");
		produto.setAtivo(Boolean.TRUE);
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produto);
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		
		Assert.assertTrue(produtoVerificacao.getAtivo());
	}
}
