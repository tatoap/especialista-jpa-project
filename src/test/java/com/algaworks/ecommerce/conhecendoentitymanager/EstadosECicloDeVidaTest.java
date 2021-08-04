package com.algaworks.ecommerce.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;

public class EstadosECicloDeVidaTest extends EntityManagerTest {
	
	@Test
	public void analisarEstados() {
		// transient
		Categoria categoriaNovo = new Categoria();
		
		// managed
		Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo);
		
		// managed
		Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1);
		
		// removed
		entityManager.remove(categoriaGerenciada);
		
		// managed
		entityManager.persist(categoriaGerenciada);
		
		// detached
		entityManager.detach(categoriaGerenciada);
	}

}
