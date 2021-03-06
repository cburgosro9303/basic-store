package com.techbox.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbox.productservice.dto.ProductDto;
import com.techbox.productservice.dto.ProductFilterDto;
import com.techbox.productservice.exception.InsufficientStockException;
import com.techbox.productservice.model.entity.Product;
import com.techbox.productservice.model.specification.GenericSpecification;
import com.techbox.productservice.repository.ProductRepository;
import com.techbox.productservice.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LogManager.getLogger(ProductServiceImpl.class);

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
    public ProductDto decreaseStock(Long productId, Long decreaseQuantity) throws InsufficientStockException {
        Long initialStock = this.currentStock(productId);
        if (initialStock >= decreaseQuantity) {
            productRepository.decreaseProductStock(productId, decreaseQuantity);
            return this.findById(productId);
        } else {
            LOG.error("There are {} units in inventory of productId {} and {} was require",
                initialStock, productId, decreaseQuantity);
            throw new InsufficientStockException();
        }
    }

    @Override
    public void increaseStock(Long productId, Long decreaseQuantity) {
        productRepository.increaseProductStock(productId, decreaseQuantity);
    }

    @Override
    public Long currentStock(Long productId) {
        return productRepository.getCurrentProductStock(productId).orElse(0L);
    }

    @Override
    public Page<ProductDto> findByFilters(Specification<ProductDto> specification, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProductDto> findAllWithFilters(List<ProductFilterDto> filters, Pageable pageable) {
        List<Specification<Product>> specifications = new ArrayList<>();
        specifications.add(new GenericSpecification.DefaultSpecification<>());
        filters.forEach(e -> {
            switch (e.getFilterTypeEmun()) {
                case EQ:
                    if (e.getProductFieldEmun().getFieldName().equals("brand")) {
                        specifications.add(new GenericSpecification.JoinEqOperation<>((String) e.getValue(), "brand", "name"));
                    } else if (e.getProductFieldEmun().getFieldName().equals("state")) {
                        specifications.add(new GenericSpecification.JoinEqOperation<>((String) e.getValue(), "state", "name"));
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
