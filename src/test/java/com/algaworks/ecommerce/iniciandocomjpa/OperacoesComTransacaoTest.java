package com.algaworks.ecommerce.iniciandocomjpa;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest {

	@Test
	public void abrirFecharTransacao() {
		
		Produto produto = new Produto(); // Somente para o metodo não mostrar erro
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produto);
		entityManager.merge(produto);
		entityManager.remove(produto);
		
		entityManager.getTransaction().commit();
	}
	
}
