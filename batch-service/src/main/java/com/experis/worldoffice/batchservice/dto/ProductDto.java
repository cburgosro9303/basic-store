package com.experis.worldoffice.batchservice.dto;

import java.math.BigDecimal;

public class ProductDto {

    private String name;
    private String brand;
    private Double price;
    private Long stock;
    private String state;
    private Integer discount;

    public ProductDto() {
    }

    public ProductDto(String name, String brand, Double price, Long stock, String state, Integer discount) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.state = state;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
            "name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            ", price=" + price +
            ", stock=" + stock +
            ", state='" + state + '\'' +
            ", discount=" + discount +
            '}';
    }
}
