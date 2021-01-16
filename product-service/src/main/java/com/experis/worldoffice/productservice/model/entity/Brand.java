package com.experis.worldoffice.productservice.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "PRODUCT", name = "BRAND")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq_gen")
    @SequenceGenerator(name = "brand_seq_gen", sequenceName = "brand_id_seq")
    @Column(name="ID")
    private Long id;

    @Column(name = "NAME",length = 100, unique = true)
    private String name;

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

    @Override
    public String toString() {
        return "Brand{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(getId(), brand.getId()) && Objects.equals(getName(), brand.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
