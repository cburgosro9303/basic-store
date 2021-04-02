package com.techbox.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDto implements Serializable {

    @NotNull
    private ProductFieldEmun productFieldEmun;

    @NotNull
    private FilterTypeEmun filterTypeEmun;

    @NotNull
    private transient Object value;

}
