package com.algaworks.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

public class EntendendoJUnitTest {
	
	@BeforeClass
	public static void iniciarTestes() {
		System.out.println(">>>> public static void iniciarTestes() <<<<");
	}
	
	@AfterClass
	public static void encerrarTestes() {
		System.out.println(">>>> public static void encerrarTestes() <<<<");
	}
	
	@Before
	public void iniciarTeste() {
		System.out.println(">>>> public void iniciarTeste() <<<<");
	}
	
	@After
	public void encerrarTeste() {
		System.out.println(">>>> public void encerrarTeste() <<<<");
	}

	@Test
	public void testandoAlgo() {
		String nome = String.format("%s", "Renato");
		
		Assert.assertEquals("Renato", nome);
	}
	
	@Test
	public void testandoOutraCoisa() {
		String nome = String.format("%s", "");
		
		Assert.assertTrue(nome.isEmpty());
	}
}
