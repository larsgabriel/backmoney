package com.moneyestudoapi.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	
	public Page<Lancamento> filtrar (LancamentoFilter filter, Pageable pageable);
}
