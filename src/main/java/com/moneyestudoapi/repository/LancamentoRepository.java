package com.moneyestudoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
