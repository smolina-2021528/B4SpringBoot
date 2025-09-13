package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Product;
import com.sebastianmolina.mechanicalshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        // Validaciones de unicidad antes de persistir
        if (productRepository.existsByProductName(product.getProductName())) {
            String msg = "productName: El nombre del producto ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            // Validaciones de unicidad que excluyen el registro actual
            if (productRepository.existsByProductNameAndIdNot(product.getProductName(), id)) {
                String msg = "productName: El nombre del producto ya está registrado por otro producto";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }

            existingProduct.setProductName(product.getProductName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}