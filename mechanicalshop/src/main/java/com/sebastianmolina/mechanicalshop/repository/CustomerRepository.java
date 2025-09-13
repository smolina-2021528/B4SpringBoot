package com.sebastianmolina.mechanicalshop.repository;

import com.sebastianmolina.mechanicalshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // estos booleanos miran si ya hay un registro con esos datos al ingresar
    boolean existsByEmail(String email);
    boolean existsByFullName(String fullName);
    boolean existsByPhone(String phone);

    // estos son para actualizar, pues buscan si otro id ya tiene ese dato
    boolean existsByEmailAndIdNot(String email, Integer id);
    boolean existsByFullNameAndIdNot(String fullName, Integer id);
    boolean existsByPhoneAndIdNot(String phone, Integer id);
}