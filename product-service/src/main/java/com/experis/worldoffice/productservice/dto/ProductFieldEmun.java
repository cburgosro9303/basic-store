package com.experis.worldoffice.productservice.dto;

import java.math.BigDecimal;

public enum ProductFieldEmun {

    ID("id"),
    NAME("name"),
    PRICE("price"),
    STOCK("stock"),
    DISCOUNT("discount"),
    STATE("state"),
    BRAND("brand");

    private String fieldName;

    ProductFieldEmun(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
