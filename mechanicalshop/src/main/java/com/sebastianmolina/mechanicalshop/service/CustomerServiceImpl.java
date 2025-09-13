package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Customer;
import com.sebastianmolina.mechanicalshop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        // Validaciones de unicidad antes de persistir
        if (customerRepository.existsByEmail(customer.getEmail())) {
            String msg = "email: El correo ya está registrado";
            System.out.println(" ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        if (customerRepository.existsByFullName(customer.getFullName())) {
            String msg = "fullName: El nombre completo ya está registrado";
            System.out.println(" ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        if (customerRepository.existsByPhone(customer.getPhone())) {
            String msg = "phone: El teléfono ya está registrado";
            System.out.println(" ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            // Validaciones de unicidad que excluyen el registro actual
            if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), id)) {
                String msg = "email: El correo ya está registrado por otro cliente";
                System.out.println(" ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
            if (customerRepository.existsByFullNameAndIdNot(customer.getFullName(), id)) {
                String msg = "fullName: El nombre completo ya está registrado por otro cliente";
                System.out.println(" ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
            if (customerRepository.existsByPhoneAndIdNot(customer.getPhone(), id)) {
                String msg = "phone: El teléfono ya está registrado por otro cliente";
                System.out.println(" ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }

            existingCustomer.setFullName(customer.getFullName());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setEmail(customer.getEmail());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}