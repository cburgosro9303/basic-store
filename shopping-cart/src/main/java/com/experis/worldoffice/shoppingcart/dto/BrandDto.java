package com.experis.worldoffice.shoppingcart.dto;

import java.io.Serializable;
import java.util.Objects;

public class BrandDto implements Serializable {

    private Long id;
    private String name;

    public BrandDto() {
    }

    public BrandDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
        BrandDto brand = (BrandDto) o;
        return Objects.equals(getId(), brand.getId()) && Objects.equals(getName(), brand.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
