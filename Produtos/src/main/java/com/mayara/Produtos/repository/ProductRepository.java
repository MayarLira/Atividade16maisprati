package com.mayara.Produtos.repository;

import com.mayara.Produtos.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Produto, Long> {
}
