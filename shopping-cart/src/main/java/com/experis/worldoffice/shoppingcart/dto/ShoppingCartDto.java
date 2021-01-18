package com.experis.worldoffice.shoppingcart.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShoppingCartDto implements Serializable {

    private Long id;
    private Long purchaseId;
    private Date shopDate;
    private ShopStateEnum shopStateEnum;
    private List<CartProductDto> cartProducts;

    public ShoppingCartDto() {
        this.cartProducts = cartProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }


    public Date getShopDate() {
        return shopDate;
    }

    public void setShopDate(Date shopDate) {
        this.shopDate = shopDate;
    }

    public ShopStateEnum getShopStateEnum() {
        return shopStateEnum;
    }

    public void setShopStateEnum(ShopStateEnum shopStateEnum) {
        this.shopStateEnum = shopStateEnum;
    }

    public List<CartProductDto> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductDto> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @Override
    public String toString() {
        return "ShoppingCartDto{" +
            "id=" + id +
            ", purchaseId=" + purchaseId +
            ", shopDate=" + shopDate +
            ", shopStateEnum=" + shopStateEnum +
            ", cartProductDto=" + cartProducts +
            '}';
    }
}
