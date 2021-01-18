package com.experis.worldoffice.shoppingcart.model.entity;

import com.experis.worldoffice.shoppingcart.dto.ShopStateEnum;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "SHOP", name = "SHOPPING_CART")
@Audited
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_cart_seq_gen")
    @SequenceGenerator(name = "shopping_cart_seq_gen", sequenceName = "shop.shopping_cart_id_seq",allocationSize = 1)
    @Column(name="ID")
    private Long id;

    @Column(name = "PURCHASE_ID",nullable = false)
    private Long purchaseId;

    @Column(name = "SHOP_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date shopDate;

    @Column(name = "SHOP_STATE",nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopStateEnum shopStateEnum;

    @OneToMany(mappedBy = "shoppingCart",fetch = FetchType.EAGER)
    @NotAudited
    private List<CartProduct> cartProducts;


    public ShoppingCart() {
        this.cartProducts = new ArrayList<>();
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

    public void setPurchaseId(Long userId) {
        this.purchaseId = userId;
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

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
            "id=" + id +
            ", userId=" + purchaseId +
            ", shopDate=" + shopDate +
            ", shopStateEnum=" + shopStateEnum +
            ", cartProducts=" + cartProducts +
            '}';
    }
}
