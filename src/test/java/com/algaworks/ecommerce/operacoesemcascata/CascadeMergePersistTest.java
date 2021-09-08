package com.algaworks.ecommerce.operacoesemcascata;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;

public class CascadeMergePersistTest extends EntityManagerTest {
	
	@Test
	public void atualizarProdutoComCategoria() {
		Produto produto = new Produto();
		produto.setId(1);
		produto.setDataUltimaAtualizacao(LocalDateTime.now());
		produto.setPreco(new BigDecimal(500));
		produto.setNome("Kindle");
		produto.setDescricao("Agora com ilumina��o embutida ajust�vel.");
		
		Categoria categoria = new Categoria();
		categoria.setId(2);
		categoria.setNome("Tablets");
		
		produto.setCategorias(Arrays.asList(categoria)); // CascadeType.MERGE
		
		entityManager.getTransaction().begin();
		entityManager.merge(produto);
		entityManager.getTransaction().commit();
		
		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		//Assert.assertTrue(categoriaVerificacao.getNome().equals("Tablets"));
		Assert.assertEquals("Tablets", categoriaVerificacao.getNome());
	}
	
	//@Test
	public void atualizarPedidoComItens() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setId(1);
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.getId().setPedidoId(pedido.getId());
		itemPedido.getId().setProdutoId(produto.getId());
		itemPedido.setProduto(produto);
		itemPedido.setPedido(pedido);
		itemPedido.setQuantidade(3);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		pedido.setItensPedido(Arrays.asList(itemPedido)); // CascadeType.MERGE
		
		entityManager.getTransaction().begin();
		entityManager.merge(pedido);
		entityManager.getTransaction().commit();
		
		ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
		Assert.assertTrue(itemPedidoVerificacao.getQuantidade().equals(3));
	}
	
	//@Test
	public void atuaizarItemPedidoComPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setId(1);
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.PAGO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.getId().setPedidoId(pedido.getId());
		itemPedido.getId().setProdutoId(produto.getId());
		itemPedido.setPedido(pedido); // CascadeType.MERGE
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(5);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		pedido.setItensPedido(Arrays.asList(itemPedido));
		
		entityManager.getTransaction().begin();
		entityManager.merge(itemPedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
		Assert.assertTrue(StatusPedido.PAGO.equals(itemPedidoVerificacao.getPedido().getStatus()));
	}

}
