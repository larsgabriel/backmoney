package com.moneyestudoapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="categoria")
@Data
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	//Esta anotação faz com que valide se o campo é null... no controller ou onde ela é recebida é necessario colocar
	//@Valid para que seja validado o objeto 
	@NotNull
	private String nome;
	
}
