package com.algaworks.ecommerce.operacoesemcascata;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.algaworks.ecommerce.model.SexoCliente;
import com.algaworks.ecommerce.model.StatusPedido;

public class CascadeTypePersistTest extends EntityManagerTest {
	
	//@Test
	public void persistirProdutoComCategoria() {
		Produto produto = new Produto();
		produto.setNome("Samsung Galaxy S21");
		produto.setDescricao("O melhor smartphone do mercado");
		produto.setPreco(BigDecimal.TEN);
		produto.setDataCriacao(LocalDateTime.now());
		
		Categoria categoria = new Categoria();
		categoria.setNome("Smartphone");
		
		produto.setCategorias(Arrays.asList(categoria)); // CascadeType.PERSIST
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		
		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		Assert.assertNotNull(categoriaVerificacao);
	}

	//@Test
	public void persistirPedidoComItens() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(produto.getPreco());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(1);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		pedido.setItensPedido(Arrays.asList(itemPedido)); // CascadeType.PERSIST
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao);
		Assert.assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
	}
	
	@Test
	public void persistirItemPedidoComPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(produto.getPreco());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido); // N�o � necess�rio CascadeType.PERSIST porque possui @MapsId.
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(1);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		entityManager.getTransaction().begin();
		entityManager.persist(itemPedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao);
	}
	
	//@Test
	public void persistirPedidoComCliente() {
		Cliente cliente = new Cliente();
		cliente.setDataNascimento(LocalDate.of(1980, 1, 1));
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setNome("Jos� Carlos");
		cliente.setCpf("01234567890");
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente); // CascadeType.PERSIST
		pedido.setTotal(BigDecimal.ZERO);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao);
	}
	
}
