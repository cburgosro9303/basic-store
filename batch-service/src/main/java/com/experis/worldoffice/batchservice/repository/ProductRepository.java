package com.experis.worldoffice.batchservice.repository;

import com.experis.worldoffice.batchservice.model.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
