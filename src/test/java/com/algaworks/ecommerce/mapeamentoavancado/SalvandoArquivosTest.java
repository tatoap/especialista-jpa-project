package com.algaworks.ecommerce.mapeamentoavancado;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class SalvandoArquivosTest extends EntityManagerTest {
	
	@Test
	public void salvarXmlNota() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml(carregarNotaFiscal());
		
		entityManager.getTransaction().begin();
		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
		Assert.assertNotNull(notaFiscalVerificacao.getXml());
		Assert.assertTrue(notaFiscalVerificacao.getXml().length > 0);
		
		/*try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(
                            System.getProperty("user.home") + "/xml.xml")).toFile());
            out.write(notaFiscalVerificacao.getXml());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
	}
	
	@Test
	public void salvarJpgFotoProduto() {
		Produto produto = new Produto();
		
		produto.setNome("Dot Alexa");
		produto.setDescricao("Gerencie sua casa apenas com comandos de voz");
		produto.setPreco(new BigDecimal(299.00));
		produto.setDataCriacao(LocalDateTime.now());
		produto.setFotoProduto(carregarFotoProduto());
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao.getFotoProduto());
		Assert.assertTrue(produtoVerificacao.getFotoProduto().length > 0);
		
		/*try {
	        OutputStream out = new FileOutputStream(
	                Files.createFile(Paths.get(
	                        System.getProperty("user.home") + "/alexa.jpg")).toFile());
	        out.write(produtoVerificacao.getFotoProduto());
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }*/
		
	}
	
	private static byte[] carregarNotaFiscal() {
		try {
			return SalvandoArquivosTest.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] carregarFotoProduto() {
		try {
			return SalvandoArquivosTest.class.getResourceAsStream("/alexa.jpg").readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
