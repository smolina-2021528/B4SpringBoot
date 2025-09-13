package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Integer id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Integer id, Customer customer);

    void deleteCustomer(Integer id);
}