package com.sebastianmolina.mechanicalshop.controller;

import com.sebastianmolina.mechanicalshop.model.Product;
import com.sebastianmolina.mechanicalshop.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        Product p = productService.getProductById(id);
        if (p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se encontro el producto"));
        return ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @Valid @RequestBody Product product){
        Product updated = productService.updateProduct(id, product);
        if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se encontro el producto"));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
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