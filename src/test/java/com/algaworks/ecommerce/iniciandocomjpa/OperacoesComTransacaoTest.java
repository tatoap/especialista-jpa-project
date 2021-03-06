package com.algaworks.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest {
	
	@Test
	public void impedirOperacaoComBancoDeDados() {
		Produto produto = entityManager.find(Produto.class, 1);
		entityManager.detach(produto); // entityManager deixa de gerenciar essa inst?ncia, impedindo qualquer opera??o com o banco de dados
		
		entityManager.getTransaction().begin();
		produto.setNome("Kindle Paperwhite 2? Gera??o");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertEquals("Kindle", produtoVerificacao.getNome());
	}
	
	@Test
	public void mostrarDiferencaPersistMerge() {
		Produto produtoPersist = new Produto();
		
		//produtoPersist.setId(5); foi comentado porque estamos usando a estrat?gia IDENTITY
		produtoPersist.setNome("Smartphone One Plus");
		produtoPersist.setDescricao("O processador mais r?pido");
		produtoPersist.setPreco(new BigDecimal(2000));
		produtoPersist.setDataCriacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		entityManager.persist(produtoPersist);
		produtoPersist.setNome("Smartphone Two Plus");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto ProdutoVerificacaoPersist = entityManager.find(Produto.class, produtoPersist.getId());
		Assert.assertNotNull(ProdutoVerificacaoPersist);
		
		Produto produtoMerge = new Produto();
		
		//produtoMerge.setId(6); foi comentado porque estamos usando a estrat?gia IDENTITY
		produtoMerge.setNome("Notebook Dell");
		produtoMerge.setDescricao("O melhor da categoria");
		produtoMerge.setDataCriacao(LocalDateTime.now());
		produtoMerge.setPreco(new BigDecimal(2000));
		
		entityManager.getTransaction().begin();
		produtoMerge = entityManager.merge(produtoMerge);
		produtoMerge.setNome("Notebook Dell 2");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto ProdutoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
		Assert.assertNotNull(ProdutoVerificacaoMerge);
	}
	
	@Test
	public void inserirObjetoComMerge() {
		Produto produto = new Produto();
		
		//produto.setId(4); foi comentado porque estamos usando a estrat?gia IDENTITY
		produto.setNome("Microfone Rode Videmic");
		produto.setDescricao("A melhor qualidade de som!");
		produto.setPreco(new BigDecimal(1000));
		produto.setDataCriacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		produto = entityManager.merge(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
	}
	
	@Test
	public void atualizarObjetoGerenciado() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		entityManager.getTransaction().begin();
		produto.setNome("Kindle Paperwhite 2? Gera??o");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertEquals("Kindle Paperwhite 2? Gera??o", produtoVerificacao.getNome());
	}
	
	@Test
	public void atualizarObjeto() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		// produto.setId(1); foi comentado porque estamos usando a estrat?gia IDENTITY
		produto.setNome("Kindle Paperwhite");
		produto.setDescricao("Conhe?a o novo kindle");
		produto.setPreco(new BigDecimal(599));
		
		entityManager.getTransaction().begin();
		entityManager.merge(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());
	}
	
	@Test
	public void removerObjeto() {
		Produto produto = entityManager.find(Produto.class, 3);
		
		entityManager.getTransaction().begin();
		entityManager.remove(produto);
		entityManager.getTransaction().commit();
		
		//entityManager.clear(); N?o ? necess?rio na asser??o para opera??o de remo??o 
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 3);
		Assert.assertNull(produtoVerificacao);
	}
	
	@Test
	public void inserirPrimeiroObjeto() {
		Produto produto = new Produto();
		
		// produto.setId(2); foi comentado porque estamos usando a estrat?gia IDENTITY
		produto.setNome("C?mera Canon");
		produto.setDescricao("A melhor defini??o para as suas fotos!");
		produto.setDataCriacao(LocalDateTime.now());
		produto.setPreco(new BigDecimal(5000));
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
	}

	@Test
	public void abrirFecharTransacao() {
		
		//Produto produto = new Produto(); // Somente para o metodo n?o mostrar erro
		
		entityManager.getTransaction().begin();
		
		//entityManager.persist(produto);
		//entityManager.merge(produto);
		//entityManager.remove(produto);
		
		entityManager.getTransaction().commit();
	}
	
}
