package com.experis.worldoffice.productservice.dto;

import java.io.Serializable;

public class CurrectExistenceDto implements Serializable {

    private Long productId;
    private Long currentExistence;

    public CurrectExistenceDto() {
    }

    public CurrectExistenceDto(Long productId, Long currentExistence) {
        this.productId = productId;
        this.currentExistence = currentExistence;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCurrentExistence() {
        return currentExistence;
    }

    public void setCurrentExistence(Long currentExistence) {
        this.currentExistence = currentExistence;
    }

    @Override
    public String toString() {
        return "CurrectExistenceDto{" +
            "productId=" + productId +
            ", currentExistence=" + currentExistence +
            '}';
    }
}
