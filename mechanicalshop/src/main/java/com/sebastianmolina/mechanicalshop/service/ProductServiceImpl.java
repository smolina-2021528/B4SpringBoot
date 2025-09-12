package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Product;
import com.sebastianmolina.mechanicalshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;

    }

    @Override
    public List<Product> findAll() {

        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {

        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {

        return productRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {

        productRepository.deleteById(id);
    }
}
