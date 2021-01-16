package com.experis.worldoffice.productservice.controller;

import com.experis.worldoffice.productservice.dto.RequestFilterDto;
import com.experis.worldoffice.productservice.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping({"/product","/product/"})
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<?> index(@RequestParam(value = "page",defaultValue = "0")int page,
                                   @RequestParam(value = "size",defaultValue = "10")int size,
                                   @RequestBody(required = false)RequestFilterDto requestFilter){
        Pageable pageable = PageRequest.of(page, size);
        if(Objects.isNull(requestFilter) || Objects.isNull(requestFilter.getFilters())
            || requestFilter.getFilters().isEmpty()) {
            return ResponseEntity.ok(this.productService.findAll(pageable));
        }
        return ResponseEntity.ok(this.productService.findAllWithFilters(requestFilter.getFilters(), pageable));
    }


}
