package com.experis.worldoffice.shoppingcart.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(schema = "SHOP", name = "CART_PRODUCT")
@Audited
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_product_seq_gen")
    @SequenceGenerator(name = "cart_product_seq_gen", sequenceName = "shop.cart_product_id_seq",allocationSize = 1)
    @Column(name="ID")
    private Long id;

    @Column(name = "original_product_id",nullable = false)
    private Long originalProductId;

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "BRAND",nullable = false)
    private String brand;


    @Column(name = "PRICE",nullable = false)
    private BigDecimal price;

    @Column(name = "DISCOUNT",nullable = false)
    private Float discount;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="SHOPPING_CART_ID", nullable=false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ShoppingCart shoppingCart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getOriginalProductId() {
        return originalProductId;
    }

    public void setOriginalProductId(Long originalProductId) {
        this.originalProductId = originalProductId;
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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            ", price=" + price +
            ", discount=" + discount +
            ", originalProductId=" + originalProductId +
            '}';
    }
}
