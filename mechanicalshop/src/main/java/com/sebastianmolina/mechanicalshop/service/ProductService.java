package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product saveProduct(Product product);
    Product updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);
}