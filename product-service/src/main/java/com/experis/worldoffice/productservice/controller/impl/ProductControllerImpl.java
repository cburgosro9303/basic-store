package com.experis.worldoffice.productservice.controller.impl;

import com.experis.worldoffice.productservice.controller.ProductController;
import com.experis.worldoffice.productservice.dto.AlterStockDto;
import com.experis.worldoffice.productservice.dto.CurrentExistenceDto;
import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.dto.RequestFilterDto;
import com.experis.worldoffice.productservice.exception.InsufficientStockException;
import com.experis.worldoffice.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping({"/product",})
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public ResponseEntity<Page<ProductDto>> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.productService.findAll(pageable));
    }

    @GetMapping(name="/existence",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrentExistenceDto> currentExistence(@PathParam("productId") Long productId) {
        CurrentExistenceDto currentExistenceDto = new CurrentExistenceDto(productId, this.productService.currentStock(productId));
        return ResponseEntity.ok(currentExistenceDto);
    }

    @PostMapping(name="/",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductDto>> indexFiltered(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @RequestBody @Valid RequestFilterDto requestFilter) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.productService.findAllWithFilters(requestFilter.getFilters(), pageable));
    }

    @PostMapping(value = "/decreaseStock",produces = MediaType.ALL_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> decreaseStock(@Valid @RequestBody AlterStockDto alterStockDto) throws InsufficientStockException {
        return productService.decreaseStock(alterStockDto.getProductId(), alterStockDto.getOperationValue()) ?
            ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/increaseStock",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> increaseStock(@Valid @RequestBody AlterStockDto alterStockDto) {
        productService.increaseStock(alterStockDto.getProductId(), alterStockDto.getOperationValue()) ;
        return ResponseEntity.ok().build();
    }


}
