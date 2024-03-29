package com.algaworks.ecommerce.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class FlushTest extends EntityManagerTest {
	
	@Test(expected = Exception.class)
	public void chamarFlush() {
		try {
			entityManager.getTransaction().begin();
			
			Pedido pedido = entityManager.find(Pedido.class, 1);
			pedido.setStatus(StatusPedido.PAGO);
			
			// ele for�a o JPA a sincronizar o que tem na mem�ria para o banco de dados
			entityManager.flush();
			
			if (pedido.getPagamento() == null) {
				throw new RuntimeException("Pedido ainda n�o foi pago.");
			}
			
			// uma consulta obriga o JPA a sincronizar o que ele tem na mem�ria (sem usar o flush explicitamente).
			// Pedido pedidoPago = entityManager
			// 		.createQuery("select p from Pedido p where p.id = 1", Pedido.class)
			// 		.getSingleResult();
			// Assert.assertEquals(pedido.getStatus(), pedidoPago.getStatus());
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
	}

}
