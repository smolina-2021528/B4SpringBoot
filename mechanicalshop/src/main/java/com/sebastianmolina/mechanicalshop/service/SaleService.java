package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Sale;

import java.util.List;
import java.util.Map;

public interface SaleService {
    List<Sale> getAllSales();
    Sale getSaleById(Integer id);
    Sale saveSale(Sale sale);
    Sale saveSaleFromMap(Map<String, Object> saleData);
    Sale updateSale(Integer id, Sale sale);
    Sale updateSaleFromMap(Integer id, Map<String, Object> saleData);
    void deleteSale(Integer id);
}