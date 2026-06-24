package com.cdi.bdccspringmvc.repository;

import com.cdi.bdccspringmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
