package com.experis.worldoffice.productservice.service.impl;

import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.dto.ProductFilterDto;
import com.experis.worldoffice.productservice.model.entity.Brand;
import com.experis.worldoffice.productservice.model.entity.Product;
import com.experis.worldoffice.productservice.model.specification.GenericSpecification;
import com.experis.worldoffice.productservice.repository.ProductRepository;
import com.experis.worldoffice.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper om;

    public ProductServiceImpl(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.om = objectMapper;
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return this.om.convertValue(product, ProductDto.class); // one option to map into dto object is using ObjectMapper
    }

    @Override
    public Page<ProductDto> findByName(String name, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(name, pageable); // We can use method query
        return new PageImpl<>(
            products.stream() // We use a stream for map and collect and build a new Page<ProductDto> object
                .map(e -> om.convertValue(e, ProductDto.class))
                .collect(Collectors.toList()),
            products.getPageable(), products.getTotalElements());
    }

    @Override
    public Page<ProductDto> findByBrandId(Long id, Pageable pageable) {
        return productRepository.findAllProductsByBrandId(id, pageable);
    }

    @Override
    public Page<ProductDto> findByBrandName(String name, Pageable pageable) {
        return productRepository.findAllProductsByBrandName(name, pageable);
    }

    @Override
    public Page<ProductDto> findByPriceRange(Long minor, Long major, Pageable pageable) {
        return productRepository.findByPriceBetween(new BigDecimal(minor), new BigDecimal(major), pageable);
    }

    @Override
    public Boolean decreaseStock(Long productId, Long decreaseQuantity) {
        Long initialStock = this.currentStock(productId);
        productRepository.decreaseProductStock(productId, decreaseQuantity);
        Long finalStock = this.currentStock(productId);
        return initialStock - finalStock == decreaseQuantity;
    }

    @Override
    public Long currentStock(Long productId) {
        return productRepository.getCurrentProductStock(productId);
    }

    @Override
    public Page<ProductDto> findByFilters(Specification<ProductDto> specification, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProductDto> findAllWithFilters(List<ProductFilterDto> filters, Pageable pageable) {
        List<Specification<Product>> specifications= new ArrayList<>();
        specifications.add(new GenericSpecification.DefaultSpecification<>());
        filters.forEach(e -> {
            switch (e.getFilterTypeEmun()) {
                case EQ:
                    if (e.getProductFieldEmun().getFieldName().equals("brand")) {
                        specifications.add(new GenericSpecification.JoinEqOperation<Product, Brand>((String) e.getValue(), "brand", "name"));
                    } else if (e.getProductFieldEmun().getFieldName().equals("state")) {
                        specifications.add(new GenericSpecification.JoinEqOperation<Product, Brand>((String) e.getValue(), "state", "name"));
                    } else {
                        specifications.add(new GenericSpecification.Eq<>((String) e.getValue(), e.getProductFieldEmun().getFieldName()));
                    }
                    break;
                case GTE:
                    specifications.add(new GenericSpecification.GTE<>(new BigDecimal(e.getValue().toString()), e.getProductFieldEmun().getFieldName()));
                    break;
                case LTE:
                    specifications.add(new GenericSpecification.LTE<>(new BigDecimal(e.getValue().toString()), e.getProductFieldEmun().getFieldName()));
                    break;
                case LIKE:
                    specifications.add(new GenericSpecification.Like<>(e.getValue().toString(), e.getProductFieldEmun().getFieldName()));
                    break;
                default:
                    specifications.add(new GenericSpecification.DefaultSpecification<>());
                    break;
            }
        });
        Specification<Product> compiledSpecification = new GenericSpecification.CompileSpecificationList<>(specifications);
        Page<Product> products = productRepository.findAll(compiledSpecification, pageable); // We can use method query
        return new PageImpl<>(
            products.stream() // We use a stream for map and collect and build a new Page<ProductDto> object
                .map(e -> om.convertValue(e, ProductDto.class))
                .collect(Collectors.toList()),
            products.getPageable(), products.getTotalElements());
    }

    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable); // We can use method query
        return new PageImpl<>(
            products.stream() // We use a stream for map and collect and build a new Page<ProductDto> object
                .map(e -> om.convertValue(e, ProductDto.class))
                .collect(Collectors.toList()),
            products.getPageable(), products.getTotalElements());
    }
}
