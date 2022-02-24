package com.algaworks.ecommerce.detalhesimportantes;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;

public class EntityGraphTest extends EntityManagerTest {
	
	@Test
	public void buscarAtributosEssenciaisPedido02() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total");
		
		Subgraph<Cliente> subgraphCliente = entityGraph.addSubgraph("cliente", Cliente.class);
		subgraphCliente.addAttributeNodes("nome", "cpf");
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
	}
	
	//@Test
	public void buscarAtributosEssenciaisPedido() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total", "notaFiscal");
		
		
        /*Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        //properties.put("javax.persistence.loadgraph", entityGraph);
        Pedido pedido = entityManager.find(Pedido.class, 1, properties);
        Assert.assertNotNull(pedido);*/
        
        TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        
	}

}
