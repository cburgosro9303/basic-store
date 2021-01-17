package com.experis.worldoffice.productservice.unit.controller;

import com.experis.worldoffice.productservice.dto.CurrectExistenceDto;
import com.experis.worldoffice.productservice.dto.DecreaseStockDto;
import com.experis.worldoffice.productservice.dto.RequestFilterDto;
import com.experis.worldoffice.productservice.exception.InsufficientStockException;
import com.experis.worldoffice.productservice.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping({"/product",})
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public ResponseEntity<?> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.productService.findAll(pageable));
    }

    @GetMapping(name="/{id}/existence",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> currentExistence(@PathVariable("id") Long productId) {
        CurrectExistenceDto currectExistenceDto = new CurrectExistenceDto(productId, this.productService.currentStock(productId));
        return ResponseEntity.ok(currectExistenceDto);
    }

    @PostMapping(name="/",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> indexFiltered(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @RequestBody @Valid RequestFilterDto requestFilter) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.productService.findAllWithFilters(requestFilter.getFilters(), pageable));
    }

    @PostMapping(value = "/decreaseStock",produces = MediaType.ALL_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> decreaseStock(@RequestBody @Valid DecreaseStockDto decreaseStockDto) throws InsufficientStockException {
        return productService.decreaseStock(decreaseStockDto.getProductId(), decreaseStockDto.getDecreaseValue()) ?
            ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


}
