package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Employee;
import com.sebastianmolina.mechanicalshop.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;

    }


    @Override
    public List<Employee> findAll() {

        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Integer id) {

        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Integer id) {

        employeeRepository.deleteById(id);
    }
}
