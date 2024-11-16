package com.mayara.Produtos.repository;

import com.mayara.Produtos.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateAndFindProduto() {
        // Arrange
        Produto produto = Produto.builder()
                .nome("Celular")
                .descricao("Smartphone de última geração")
                .preco(2000.0)
                .quantidadeEstoque(5)
                .build();

        // Act
        Produto savedProduto = productRepository.save(produto);
        Optional<Produto> foundProduto = productRepository.findById(savedProduto.getId());

        // Assert
        assertTrue(foundProduto.isPresent());
        assertEquals("Celular", foundProduto.get().getNome());
    }

    @Test
    public void testDeleteProduto() {
        // Arrange
        Produto produto = Produto.builder()
                .nome("Fone de Ouvido")
                .descricao("Fone com cancelamento de ruído")
                .preco(500.0)
                .quantidadeEstoque(10)
                .build();
        Produto savedProduto = productRepository.save(produto);

        // Act
        productRepository.deleteById(savedProduto.getId());
        Optional<Produto> deletedProduto = productRepository.findById(savedProduto.getId());

        // Assert
        assertFalse(deletedProduto.isPresent());
    }
}
