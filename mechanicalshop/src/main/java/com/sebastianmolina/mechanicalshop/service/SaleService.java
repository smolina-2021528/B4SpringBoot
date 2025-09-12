package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Sale;
import java.util.List;
import java.util.Optional;


public interface SaleService {

    List<Sale> findAll();

    Optional<Sale> findById(Integer id);

    Sale save(Sale sale);

    void deleteById(Integer id);

}
