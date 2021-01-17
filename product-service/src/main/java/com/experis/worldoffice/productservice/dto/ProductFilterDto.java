package com.experis.worldoffice.productservice.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ProductFilterDto implements Serializable {

    @NotNull
    private ProductFieldEmun productFieldEmun;

    @NotNull
    private FilterTypeEmun filterTypeEmun;

    @NotNull
    private transient Object value;

    public ProductFilterDto() {
    }

    public ProductFieldEmun getProductFieldEmun() {
        return productFieldEmun;
    }

    public void setProductFieldEmun(ProductFieldEmun productFieldEmun) {
        this.productFieldEmun = productFieldEmun;
    }

    public FilterTypeEmun getFilterTypeEmun() {
        return filterTypeEmun;
    }

    public void setFilterTypeEmun(FilterTypeEmun filterTypeEmun) {
        this.filterTypeEmun = filterTypeEmun;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProductFilterDto{" +
            "productFieldEmun=" + productFieldEmun +
            ", filterTypeEmun=" + filterTypeEmun +
            ", value=" + value +
            '}';
    }
}
