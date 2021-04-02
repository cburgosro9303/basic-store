package com.techbox.productservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long stock;
    private Float discount;
    private StateDto state;
    private BrandDto brand;

    public ProductDto(Long id, String name, BigDecimal price, Long stock, Float discount, StateDto state, BrandDto brand) { //NOSONAR
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        this.state = state;
        this.brand = brand;
    }

    public ProductDto(Long id, String name, BigDecimal price, Long stock, Float discount, Long stateId, //NOSONAR
                      String stateName, Long brandId, String brandName) {
        this(id, name, price, stock, discount, new StateDto(stateId, stateName), new BrandDto(brandId, brandName));

    }
}
