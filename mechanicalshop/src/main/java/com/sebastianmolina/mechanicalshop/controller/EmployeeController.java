package com.sebastianmolina.mechanicalshop.controller;

import com.sebastianmolina.mechanicalshop.model.Employee;
import com.sebastianmolina.mechanicalshop.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id){
        Employee e = employeeService.getEmployeeById(id);
        if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se encontro el empleado"));
        return ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @Valid @RequestBody Employee employee){
        Employee updated = employeeService.updateEmployee(id, employee);
        if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se encontro el empleado"));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployee(id);
    }

    // aqui manejo mis errores
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //es un hash map para poder guardar los errores con su mensaje
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // aqui me devuelve todos los errores para validar
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            // para cada error da el nombre del campo donde hay problema y llama el mensaje
            errors.put(error.getField(), error.getDefaultMessage());
            System.out.println("Problema en el campo " + error.getField() + " : " + error.getDefaultMessage());
        });
        // devulelve un bad request y el error como tal
        return ResponseEntity.badRequest().body(errors);
    }

    // aqui capturo las excepciones que hacen reventar el codigo
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        // traemos el mensaje del error
        String msg = ex.getMessage();
        // si el mensaje no es vacio, divide el mensaje
        if (msg != null && msg.contains(":")) {
            String[] parts = msg.split(":", 2);
            // primero coloca el campo donde hay error
            String field = parts[0].trim();
            // llama ahora si el mensaje como tal
            String message = parts[1].trim();
            errors.put(field, message);
            System.out.println("Problema en el campo " + field + " : " + message);
        } else {
            // si el mensaje no tiene contenido da un error como tal
            errors.put("error", msg == null ? "Error en la operacion" : msg);
            System.out.println("Problema : " + msg);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    // este maneja las excepciones para la base de datos porque a veces revienta por eso
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String detail = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        System.out.println("Problema sql : " + detail);
        Map<String, String> err = new HashMap<>();
        err.put("error", "Valor duplicado o alguna restriccion " + (detail != null ? detail : ""));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }
}