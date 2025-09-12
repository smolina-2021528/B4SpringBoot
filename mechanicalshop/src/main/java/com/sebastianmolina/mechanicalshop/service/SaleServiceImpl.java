package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Sale;
import com.sebastianmolina.mechanicalshop.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    public SaleServiceImpl(SaleRepository saleRepository) {

        this.saleRepository = saleRepository;

    }

    @Override
    public List<Sale> findAll() {

        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> findById(Integer id) {

        return saleRepository.findById(id);
    }

    @Override
    public Sale save(Sale sale) {

        return saleRepository.save(sale);
    }

    @Override
    public void deleteById(Integer id) {

        saleRepository.deleteById(id);
    }
}
