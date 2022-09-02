package com.algaworks.ecommerce.detalhesimportantes;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class OneToOneLazyTest extends EntityManagerTest {

	@Test
    public void mostrarProblema() {
        System.out.println("BUSCANDO UM PEDIDO:");
        Pedido pedido = entityManager.find(Pedido.class, 1);
        Assert.assertNotNull(pedido);

        System.out.println("----------------------------------------------------");

        System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
        List<Pedido> lista = entityManager
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
	
}
