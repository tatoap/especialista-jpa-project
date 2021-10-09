package com.algaworks.ecommerce.criteria;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;

public class PassandoParametrosCriteriaTest extends EntityManagerTest {

	@Test
	public void passandoParametros() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<NotaFiscal> criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class);
		Root<NotaFiscal> root = criteriaQuery.from(NotaFiscal.class);
		
		criteriaQuery.select(root);
		
		ParameterExpression<Date> parameterExpressionDate = criteriaBuilder
				.parameter(Date.class, "dataInicial");
		
		criteriaQuery.where(criteriaBuilder.greaterThan(root.get("dataEmissao"), parameterExpressionDate));
		
		TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(criteriaQuery);
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial.add(Calendar.DATE, -30);
		
		typedQuery.setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);
		
		List<NotaFiscal> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

	}
	
}
