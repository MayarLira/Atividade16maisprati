package com.mayara.Produtos.service;

import com.mayara.Produtos.model.Produto;
import com.mayara.Produtos.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProdutoService produtoService;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetAllProdutos() {
        // Arrange
        Produto produto1 = Produto.builder().id(1L).nome("Teclado").descricao("Teclado Mecânico").preco(300.0).quantidadeEstoque(10).build();
        Produto produto2 = Produto.builder().id(2L).nome("Mouse").descricao("Mouse Gamer").preco(150.0).quantidadeEstoque(20).build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        // Act
        List<Produto> produtos = produtoService.getAllProdutos();

        // Assert
        assertEquals(2, produtos.size());
        assertEquals("Teclado", produtos.get(0).getNome());
        assertEquals("Mouse", produtos.get(1).getNome());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProdutoById() {
        // Arrange
        Produto produto = Produto.builder().id(1L).nome("Monitor").descricao("Monitor Full HD").preco(800.0).quantidadeEstoque(5).build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act
        Optional<Produto> foundProduto = produtoService.getProdutoById(1L);

        // Assert
        assertTrue(foundProduto.isPresent());
        assertEquals("Monitor", foundProduto.get().getNome());
        assertEquals(800.0, foundProduto.get().getPreco());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProdutoByIdNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Produto> foundProduto = produtoService.getProdutoById(1L);

        // Assert
        assertFalse(foundProduto.isPresent());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateProduto() {
        // Arrange
        Produto produto = Produto.builder().nome("Cadeira Gamer").descricao("Cadeira ergonômica").preco(1200.0).quantidadeEstoque(3).build();
        when(productRepository.save(produto)).thenReturn(produto);

        // Act
        Produto savedProduto = produtoService.createProduto(produto);

        // Assert
        assertNotNull(savedProduto);
        assertEquals("Cadeira Gamer", savedProduto.getNome());
        verify(productRepository, times(1)).save(produto);
    }

    @Test
    public void testUpdateProduto() {
        // Arrange
        Produto existingProduto = Produto.builder().id(1L).nome("Mesa").descricao("Mesa de escritório").preco(500.0).quantidadeEstoque(2).build();
        Produto updatedProduto = Produto.builder().nome("Mesa Atualizada").descricao("Mesa maior").preco(700.0).quantidadeEstoque(5).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduto));
        when(productRepository.save(existingProduto)).thenReturn(existingProduto);

        // Act
        Produto result = produtoService.updateProduto(1L, updatedProduto);

        // Assert
        assertNotNull(result);
        assertEquals("Mesa Atualizada", result.getNome());
        assertEquals(700.0, result.getPreco());
        assertEquals(5, result.getQuantidadeEstoque());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduto);
    }

    @Test
    public void testUpdateProdutoNotFound() {
        // Arrange
        Produto updatedProduto = Produto.builder().nome("Produto Inexistente").build();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> produtoService.updateProduto(1L, updatedProduto));
        assertEquals("Produto não encontrado com o ID: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(0)).save(any());
    }

    @Test
    public void testDeleteProduto() {
        // Arrange
        Produto produto = Produto.builder().id(1L).nome("Impressora").descricao("Impressora Multifuncional").preco(1500.0).quantidadeEstoque(1).build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(produto));
        doNothing().when(productRepository).deleteById(1L);

        // Act
        produtoService.deleteProduto(1L);

        // Assert
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProdutoNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> produtoService.deleteProduto(1L));
        assertEquals("Produto não encontrado com o ID: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(0)).deleteById(anyLong());
    }
}