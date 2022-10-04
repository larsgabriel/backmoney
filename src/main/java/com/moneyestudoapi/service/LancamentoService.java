package com.moneyestudoapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.model.Pessoa;
import com.moneyestudoapi.repository.LancamentoRepository;
import com.moneyestudoapi.repository.PessoaRepository;
import com.moneyestudoapi.service.exception.PessoaInexistenteOuInativaException;

@Service		
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if(pessoa.get() == null || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

}
