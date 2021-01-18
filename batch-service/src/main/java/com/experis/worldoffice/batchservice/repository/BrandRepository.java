package com.experis.worldoffice.batchservice.repository;

import com.experis.worldoffice.batchservice.model.entity.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BrandRepository extends PagingAndSortingRepository<Brand,Long> {

    Optional<Brand> findByNameIgnoreCase(String name);
}
