package com.algaworks.ecommerce.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedido_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto_;

public class PathExpressionTest extends EntityManagerTest {
	
	@Test
	public void buscarPedidosComProdutoDeIDIgual1Exercicio() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, ItemPedido> joinItemPedido = root.join(Pedido_.itensPedido);
		
		criteriaQuery.where(
				criteriaBuilder.equal(joinItemPedido.get(ItemPedido_.produto).get(Produto_.id), 1));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	//@Test
	public void usarPathExpression() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.like(root.get(Pedido_.cliente).get(Cliente_.nome), "P%"));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}

}
