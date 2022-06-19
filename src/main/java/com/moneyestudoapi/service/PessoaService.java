package com.moneyestudoapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.moneyestudoapi.model.Pessoa;
import com.moneyestudoapi.repository.PessoaRepository;

@Service
public class PessoaService {
 	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	//AQUI USO O BEANUTILS para comparar os objetos e atualizar posteriormente, 
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		//copiar de para ignorando o codigo
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaRepository.save(pessoaSalva);
	}   

	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}

	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		//caso for encontrado menor que 1 lança a exceção
		Pessoa pessoaSalva =  pessoaRepository.findById(codigo)
								.orElseThrow(() -> new EmptyResultDataAccessException(1));

		return pessoaSalva;
	}
}
