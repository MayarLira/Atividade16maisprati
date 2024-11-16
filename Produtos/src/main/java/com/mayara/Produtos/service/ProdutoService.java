package com.mayara.Produtos.service;

import com.mayara.Produtos.model.Produto;
import com.mayara.Produtos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProductRepository productRepository;

    // Setter for testing purposes
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Produto> getAllProdutos() {
        return productRepository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return productRepository.findById(id);
    }

    public Produto createProduto(Produto produto) {
        return productRepository.save(produto);
    }

    public Produto updateProduto(Long id, Produto updatedProduto) {
        Optional<Produto> existingProdutoOpt = productRepository.findById(id);

        if (existingProdutoOpt.isPresent()) {
            Produto existingProduto = existingProdutoOpt.get();
            existingProduto.setNome(updatedProduto.getNome());
            existingProduto.setDescricao(updatedProduto.getDescricao());
            existingProduto.setPreco(updatedProduto.getPreco());
            existingProduto.setQuantidadeEstoque(updatedProduto.getQuantidadeEstoque());
            return productRepository.save(existingProduto);
        }

        throw new RuntimeException("Produto não encontrado com o ID: " + id);
    }

    public void deleteProduto(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
    }
}
