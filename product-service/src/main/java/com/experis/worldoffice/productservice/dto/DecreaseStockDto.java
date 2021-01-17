package com.experis.worldoffice.productservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class DecreaseStockDto implements Serializable {

    @NotNull
    @Positive
    @Min(1L)
    private Long productId;

    @NotNull
    @Positive
    @Min(1L)
    private Long decreaseValue;

    public DecreaseStockDto() {
    }

    public DecreaseStockDto(Long productId, Long decreaseValue) {
        this.productId = productId;
        this.decreaseValue = decreaseValue;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDecreaseValue() {
        return decreaseValue;
    }

    public void setDecreaseValue(Long decreaseValue) {
        this.decreaseValue = decreaseValue;
    }

    @Override
    public String toString() {
        return "DecreaseStockDto{" +
            "productId=" + productId +
            ", decreaseValue=" + decreaseValue +
            '}';
    }
}
