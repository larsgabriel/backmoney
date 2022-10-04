package com.moneyestudoapi.repository.lancamento;

import java.util.List;

import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	
	public List<Lancamento> filtrar (LancamentoFilter filter);
}
