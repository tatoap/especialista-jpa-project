package com.algaworks.ecommerce.conhecendoentitymanager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class ContextoDePersistenciaTest extends EntityManagerTest {

	@Test
	public void usarContextoPersistencia() {
		entityManager.getTransaction().begin();
		
		// j� esta em um contexto de persist�ncia
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setPreco(new BigDecimal(100.0));
		
		// at� a linha 24, n�o esta em um contexto de persist�ncia
		Produto produto2 = new Produto();
		produto2.setDataCriacao(LocalDateTime.now());
		produto2.setNome("Caneca para caf�");
		produto2.setPreco(new BigDecimal(10.0));
		produto2.setDescricao("Boa caneca para caf�");
		entityManager.persist(produto2);
		
		// at� a linha 31, n�o esta em um contexto de persist�ncia
		Produto produto3 = new Produto();
		produto3.setDataCriacao(LocalDateTime.now());
		produto3.setNome("Caneca para ch�");
		produto3.setPreco(new BigDecimal(10.0));
		produto3.setDescricao("Boa caneca para ch�");
		produto3 = entityManager.merge(produto3);
		
		entityManager.flush();
		
		produto3.setDescricao("Alterar descri��o");
		
		entityManager.getTransaction().commit();
	}
}
