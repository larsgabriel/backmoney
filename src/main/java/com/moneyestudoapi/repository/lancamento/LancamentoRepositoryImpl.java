package com.moneyestudoapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.filter.LancamentoFilter;
import com.moneyestudoapi.repository.projection.ProjectionLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	//Esta classe traz um exemplo de criteria com meta model, para adicionar o metamodel é necessario clicar com botão direito no projeto,
	//ir em java compiler enable annotation processing e colocar o caminho src/main/java depois importar em factorypath a lib hibernate-jpamodelgen
	//se preciso for baixe.
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
		dadospaginacao(query, pageable);
		
		return new PageImpl<>( query.getResultList(), pageable, total(filter));
	}
	
	@Override
	public Page<ProjectionLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ProjectionLancamento> criteria = builder.createQuery(ProjectionLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Lancamento lancamento = new Lancamento();
		
		criteria.select(builder.construct(ProjectionLancamento.class
				, root.get(lancamento.getCodigo().toString())
				, root.get(lancamento.getDescricao())
				, root.get(lancamento.getDataVencimento().toString())
				, root.get(lancamento.getDataPagamento().toString())
				, root.get(lancamento.getValor().toString())
				,root.get(String.valueOf(lancamento.getTipo()))
				, root.get(lancamento.getCategoria().getNome())
				, root.get(lancamento.getPessoa().getNome())));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ProjectionLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void dadospaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber(); //qual é a pagina atual
		int totalPorPagina = pageable.getPageSize(); // total definido por pagina
		// aqui qual será o proximo regitro da proxima pagina, imagine que vc esta para a pagina 2 e tem 3 registro por pagina o proximo registro
		// sera 2 * 3 = 6 lembrando que o 6 registro na verdade é o setimo pois começa a mostrar do zero.
		int primeiroRegistro = paginaAtual * totalPorPagina;
		
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalPorPagina);
		
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
		
		Lancamento lancamento = new Lancamento();
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(
					builder.like(
							builder.lower(root.get(lancamento.getDescricao())), "%" + filter.getDescricao() + "%"));
		}
		
		if(filter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(lancamento.getDataVencimento().toString()), filter.getDataVencimentoDe() ));
		}
		
		if(filter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(lancamento.getDataVencimento().toString()), filter.getDataVencimentoAte() ));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
