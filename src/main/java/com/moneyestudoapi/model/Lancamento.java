package com.moneyestudoapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "lancamento")
@Data
public class Lancamento {

	private Long codigo;
	
	private String descricao;
	
	@Column(name = "data vencimento")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	private BigDecimal valor;
	
	private String observacao;
	
	private TipoLancamento tipo;
	
	@ManyToOne //neste caso como aqui ta setado em categoria deve-se ler "uma categoria pode estar em varios lancamentos"
	@JoinColumn(name = "codigo_categoria") // aqui tem que ser setado o nome da coluna do banco
	private Categoria categoria;
	
	@ManyToOne 
	@JoinColumn(name = "codigo_pessoa") 
	private Pessoa pessoa;
	
}
