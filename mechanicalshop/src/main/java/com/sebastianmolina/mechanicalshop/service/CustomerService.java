package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerService {

    List<Customer> findAll();

    Optional<Customer> findById(Integer id);

    Customer save(Customer customer);

    void deleteById(Integer id);

}
