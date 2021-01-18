package com.experis.worldoffice.shoppingcart.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartProductDto implements Serializable {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Float discount;
    private ShoppingCartDto shoppingCartDto;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public ShoppingCartDto getShoppingCartDto() {
        return shoppingCartDto;
    }

    public void setShoppingCartDto(ShoppingCartDto shoppingCartDto) {
        this.shoppingCartDto = shoppingCartDto;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            ", price=" + price +
            ", discount=" + discount +
            ", shoppingCart=" + shoppingCartDto +
            '}';
    }
}
