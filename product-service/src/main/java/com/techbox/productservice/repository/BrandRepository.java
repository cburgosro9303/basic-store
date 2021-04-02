package com.techbox.productservice.repository;

import com.techbox.productservice.model.entity.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandRepository extends PagingAndSortingRepository<Brand,Long> {

}
