package com.moneyestudoapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moneyestudoapi.event.RecursoCriadoEvent;
import com.moneyestudoapi.model.Lancamento;
import com.moneyestudoapi.repository.LancamentoRepository;
import com.moneyestudoapi.repository.filter.LancamentoFilter;
import com.moneyestudoapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping("/pesquisar")
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping("/findbyid")
	public Optional<Lancamento> buscarPorId(Long id) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(id);
		return lancamento;

	}
	
	@PostMapping("/criarlancamento")
	public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(LancamentoController.class, response, lancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);		
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		this.lancamentoRepository.deleteById(codigo);
	}
	
}
