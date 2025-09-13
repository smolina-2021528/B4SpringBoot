package com.sebastianmolina.mechanicalshop.controller;

import com.sebastianmolina.mechanicalshop.model.Sale;
import com.sebastianmolina.mechanicalshop.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> getAll() {
        return saleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getById(@PathVariable Integer id) {
        return saleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Sale sale) {
        try {
            return ResponseEntity.ok(saleService.save(sale));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Sale sale) {
        return saleService.findById(id)
                .map(existing -> {
                    sale.setId(id);
                    try {
                        return ResponseEntity.ok(saleService.save(sale));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        saleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
