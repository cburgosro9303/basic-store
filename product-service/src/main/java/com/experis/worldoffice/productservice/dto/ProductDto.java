package com.experis.worldoffice.productservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long stock;
    private Float discount;
    private StateDto state;
    private BrandDto brand;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BigDecimal price, Long stock, Float discount, StateDto state,
                      BrandDto brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        this.state = state;
        this.brand = brand;
    }

    public ProductDto(Long id, String name, BigDecimal price, Long stock, Float discount, Long stateId,
                      String stateName, Long brandId, String brandName) {
        this(id, name, price, stock, discount, new StateDto(stateId, stateName), new BrandDto(brandId, brandName));

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public StateDto getState() {
        return state;
    }

    public void setState(StateDto state) {
        this.state = state;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", stock=" + stock +
            ", discount=" + discount +
            ", state=" + state +
            ", brand=" + brand +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto product = (ProductDto) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getName(), product.getName()) && Objects.equals(getPrice(), product.getPrice()) && Objects.equals(getStock(), product.getStock()) && Objects.equals(getDiscount(), product.getDiscount()) && Objects.equals(getState(), product.getState()) && Objects.equals(getBrand(), product.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getStock(), getDiscount(), getState(), getBrand());
    }
}
