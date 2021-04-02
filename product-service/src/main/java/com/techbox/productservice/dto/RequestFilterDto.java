package com.techbox.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RequestFilterDto {
    @Valid
    @NotEmpty
    private List<ProductFilterDto> filters;

    public RequestFilterDto() {
        this.filters = new ArrayList<>();
    }

}
