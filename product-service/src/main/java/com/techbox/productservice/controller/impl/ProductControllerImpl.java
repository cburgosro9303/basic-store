package com.techbox.productservice.controller.impl;

import com.techbox.productservice.controller.ProductController;
import com.techbox.productservice.dto.AlterStockDto;
import com.techbox.productservice.dto.CurrentExistenceDto;
import com.techbox.productservice.dto.ProductDto;
import com.techbox.productservice.dto.RequestFilterDto;
import com.techbox.productservice.exception.InsufficientStockException;
import com.techbox.productservice.service.ProductService;
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

    @GetMapping(value="/existence",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrentExistenceDto> currentExistence(@PathParam("productId") Long productId) {
        CurrentExistenceDto currentExistenceDto = new CurrentExistenceDto(productId, this.productService.currentStock(productId));
        return ResponseEntity.ok(currentExistenceDto);
    }

    @PostMapping(value="/",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductDto>> indexFiltered(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @RequestBody @Valid RequestFilterDto requestFilter) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.productService.findAllWithFilters(requestFilter.getFilters(), pageable));
    }

    @PostMapping(value = "/decreaseStock",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> decreaseStock(@Valid @RequestBody AlterStockDto alterStockDto) throws InsufficientStockException {
        return ResponseEntity.ok(productService.decreaseStock(alterStockDto.getProductId(), alterStockDto.getOperationValue()));
    }

    @PostMapping(value = "/increaseStock",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> increaseStock(@Valid @RequestBody AlterStockDto alterStockDto) {
        productService.increaseStock(alterStockDto.getProductId(), alterStockDto.getOperationValue()) ;
        return ResponseEntity.ok().build();
    }


}
