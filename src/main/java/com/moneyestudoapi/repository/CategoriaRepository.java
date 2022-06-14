package com.moneyestudoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyestudoapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
