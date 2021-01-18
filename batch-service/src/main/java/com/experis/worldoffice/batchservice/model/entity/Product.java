package com.experis.worldoffice.batchservice.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(schema = "PRODUCT", name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product.product_id_seq",allocationSize = 1)
    @Column(name="ID")
    private Long id;

    @Column(name = "NAME",length = 100, unique = true)
    private String name;

    @Column(name = "PRICE", precision = 21, scale = 6)
    private BigDecimal price;

    @Column(name = "STOCK")
    private Long stock;

    @Column(name = "discount", precision = 4, scale = 3)
    private Float discount;

    @ManyToOne
    @JoinColumn(name="PRODUCT_STATE_ID")
    private State state;

    @ManyToOne
    @JoinColumn(name="BRAND_ID")
    private Brand brand;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
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
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getName(), product.getName()) && Objects.equals(getPrice(), product.getPrice()) && Objects.equals(getStock(), product.getStock()) && Objects.equals(getDiscount(), product.getDiscount()) && Objects.equals(getState(), product.getState()) && Objects.equals(getBrand(), product.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getStock(), getDiscount(), getState(), getBrand());
    }
}
