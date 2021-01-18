package com.experis.worldoffice.shoppingcart.dto;

import java.io.Serializable;

public class AlterStockDto implements Serializable {

    private Long productId;
    private Long operationValue;

    public AlterStockDto() {
    }

    public AlterStockDto(Long productId, Long operationValue) {
        this.productId = productId;
        this.operationValue = operationValue;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOperationValue() {
        return operationValue;
    }

    public void setOperationValue(Long operationValue) {
        this.operationValue = operationValue;
    }

    @Override
    public String toString() {
        return "DecreaseStockDto{" +
            "productId=" + productId +
            ", decreaseValue=" + operationValue +
            '}';
    }
}
