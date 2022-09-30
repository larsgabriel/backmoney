package com.moneyestudoapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyestudoapi.event.RecursoCriadoEvent;
import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

	@Autowired
	LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping("/findall")
	public List<Lancamento> buscarCategorias() {
		return lancamentoRepository.findAll();
	}
	
	@GetMapping("/findbyid")
	public Optional<Lancamento> buscarPorId(Long id) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(id);
		return lancamento;

	}
	
	public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalva = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(LancamentoController.class, response, lancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);		
		
	}

}
