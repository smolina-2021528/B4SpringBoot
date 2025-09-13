package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Employee;
import com.sebastianmolina.mechanicalshop.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        // Validaciones de unicidad antes de persistir
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            String msg = "email: El correo ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        if (employeeRepository.existsByFirstName(employee.getFirstName())) {
            String msg = "firstName: El nombre ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        if (employeeRepository.existsByLastName(employee.getLastName())) {
            String msg = "lastName: El apellido ya está registrado";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            // Validaciones de unicidad que excluyen el registro actual
            if (employeeRepository.existsByEmailAndIdNot(employee.getEmail(), id)) {
                String msg = "email: El correo ya está registrado por otro empleado";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
            if (employeeRepository.existsByFirstNameAndIdNot(employee.getFirstName(), id)) {
                String msg = "firstName: El nombre ya está registrado por otro empleado";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
            if (employeeRepository.existsByLastNameAndIdNot(employee.getLastName(), id)) {
                String msg = "lastName: El apellido ya está registrado por otro empleado";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }

            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            return employeeRepository.save(existingEmployee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}