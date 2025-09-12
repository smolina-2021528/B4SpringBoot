package com.sebastianmolina.mechanicalshop.repository;

import com.sebastianmolina.mechanicalshop.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {


}
