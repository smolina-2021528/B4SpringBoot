package com.sebastianmolina.mechanicalshop.repository;

import com.sebastianmolina.mechanicalshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // estos booleanos miran si ya hay un registro con esos datos al ingresar
    boolean existsByProductName(String productName);

    // estos son para actualizar, pues buscan si otro id ya tiene ese dato
    boolean existsByProductNameAndIdNot(String productName, Integer id);
}