package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Sale;

import java.util.List;

public interface SaleService {
    List<Sale> getAllSales();
    Sale getSaleById(Integer id);
    Sale saveSale(Sale sale);
    Sale updateSale(Integer id, Sale sale);
    void deleteSale(Integer id);
}