package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Sale;
import com.sebastianmolina.mechanicalshop.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(Integer id) {
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public Sale saveSale(Sale sale) {
        // No se incluyen validaciones de unicidad ya que no hay restricciones unique en el modelo
        // Las validaciones de @NotNull para customer, employee y product se manejan en el modelo
        return saleRepository.save(sale);
    }

    @Override
    public Sale updateSale(Integer id, Sale sale) {
        Sale existingSale = saleRepository.findById(id).orElse(null);
        if (existingSale != null) {
            // No se incluyen validaciones de unicidad ya que no hay restricciones unique en el modelo
            // Las validaciones de @NotNull para customer, employee y product se manejan en el modelo
            existingSale.setCustomer(sale.getCustomer());
            existingSale.setEmployee(sale.getEmployee());
            existingSale.setProduct(sale.getProduct());
            existingSale.setTotal(sale.getTotal());
            return saleRepository.save(existingSale);
        }
        return null;
    }

    @Override
    public void deleteSale(Integer id) {
        saleRepository.deleteById(id);
    }
}