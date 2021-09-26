package com.algaworks.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class DynamicQueryTest extends EntityManagerTest {
	
	@Test
	public void executarConsultaDinamica() {
		Produto produtoConsultado = new Produto();
		produtoConsultado.setNome("Câmera GoPro");
		
		List<Produto> lista = pesquisar(produtoConsultado);
		
		Assert.assertFalse(lista.isEmpty());
		Assert.assertEquals("Câmera GoPro Hero 7", lista.get(0).getNome());
	}

	private List<Produto> pesquisar(Produto produtoConsultado) {
		StringBuilder jpql = new StringBuilder("select p from Produto p where 1 = 1");
		
		if (produtoConsultado.getNome() != null) {
			jpql.append(" and p.nome like concat('%', :nome, '%')");
		}
		
		if (produtoConsultado.getDescricao() != null) {
			jpql.append(" and p.descricao like concat('%', :descricao, '%')");
		}
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);
		
		if (produtoConsultado.getNome() != null) {
			typedQuery.setParameter("nome", produtoConsultado.getNome());
		}
		
		if (produtoConsultado.getDescricao() != null) {
			typedQuery.setParameter("descricao", produtoConsultado.getDescricao());
		}
		
		return typedQuery.getResultList();
	}

}
