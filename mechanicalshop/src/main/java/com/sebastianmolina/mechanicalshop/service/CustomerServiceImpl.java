package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Customer;
import com.sebastianmolina.mechanicalshop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;

    }

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();

    }

    @Override
    public Optional<Customer> findById(Integer id) {

        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(Integer id) {

        customerRepository.deleteById(id);
    }
}
