package com.algaworks.ecommerce.mapeamentoavancado;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;

public class PropriedadesTransientesTest extends EntityManagerTest {
	
	@Test
	public void validarPrimeiroNome() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		Assert.assertEquals("Renato", cliente.getPrimeiroNome());
	}

}
