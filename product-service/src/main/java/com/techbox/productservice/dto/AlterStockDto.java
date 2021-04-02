package com.techbox.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterStockDto implements Serializable {

    @NotNull
    @Min(1L)
    private Long productId;

    @NotNull
    @Min(1L)
    private Long operationValue;

}
