package com.algaworks.ecommerce.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class CachePrimeiroNivelTest extends EntityManagerTest {

	@Test
	public void verificarCache() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		System.out.println(produto.getNome());
		
		System.out.println("*--------------------------------------------*");
		
		// para limpar o cache...
		// entityManager.clear();
		// ou...
		// entityManager.close();
		// mas se fechar o entityManager, para usar ele novamente, teremos que usar a instrução abaixo
		// entityManager = entityManagerFactory.createEntityManager();
		
		// busca do cache de primeiro nível
		Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
		System.out.println(produtoResgatado.getNome());
	}
}
