package com.algaworks.ecommerce.mapeamentoavancado;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.PagamentoCartao;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.SexoCliente;
import com.algaworks.ecommerce.model.StatusPagamento;

public class HerancaTest extends EntityManagerTest {

	@Test
	public void salvarCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setNome("Eliana Soares");
		cliente.setDataNascimento(LocalDate.of(1958, 12, 1));
		cliente.setSexo(SexoCliente.FEMININO);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao.getNome());
	}
	
	@Test
	public void buscarPagamentos() {
		List<Pagamento> pagamentos = entityManager.createQuery("select p from Pagamento p").getResultList();
		
		Assert.assertFalse(pagamentos.isEmpty());
	}
	
	@Test
	public void incluirPagamentoPedido() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		PagamentoCartao pagamentoCartao = new PagamentoCartao();
		pagamentoCartao.setPedido(pedido);
		pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
		pagamentoCartao.setNumeroCartao("123");
		
		entityManager.getTransaction().begin();
		entityManager.persist(pagamentoCartao);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao.getPagamento());
	}
	
}
