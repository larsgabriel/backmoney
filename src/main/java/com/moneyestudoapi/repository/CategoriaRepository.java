package com.moneyestudoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyestudoapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	List<Categoria> findByCodigo(Long codigo);

}
