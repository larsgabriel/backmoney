package com.moneyestudoapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.filter.LancamentoFilter;
import com.moneyestudoapi.repository.projection.ProjectionLancamento;

public interface LancamentoRepositoryQuery {

	
	public Page<Lancamento> filtrar (LancamentoFilter filter, Pageable pageable);

	Page<ProjectionLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
