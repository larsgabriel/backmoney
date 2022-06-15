package com.moneyestudoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyestudoapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
