package com.experis.worldoffice.productservice.service;

import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.dto.ProductFilterDto;
import com.experis.worldoffice.productservice.exception.InsufficientStockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {

    ProductDto findById(Long id);
    Page<ProductDto> findByName(String name, Pageable pageable);
    Page<ProductDto> findByBrandId(Long id, Pageable pageable);
    Page<ProductDto> findByBrandName(String name, Pageable pageable);
    Page<ProductDto> findByPriceRange(Long minor,Long major, Pageable pageable);
    Boolean decreaseStock(Long productId, Long decreaseQuantity) throws InsufficientStockException;
    Long currentStock(Long productId);
    Page<ProductDto> findByFilters(Specification<ProductDto> specification,Pageable pageable);

    /**
     * Build a Specifitation<Product> with filter list and compile in one for make a query method with Repository
     * @param filters
     * @param pageable
     * @return
     */
    Page<ProductDto> findAllWithFilters(List<ProductFilterDto> filters, Pageable pageable);
    Page<ProductDto> findAll(Pageable pageable);
}
