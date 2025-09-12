package com.sebastianmolina.mechanicalshop.repository;

import com.sebastianmolina.mechanicalshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByFullName(String fullName);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

}
