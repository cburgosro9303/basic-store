package com.experis.worldoffice.productservice.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestFilterDto {
    private List<ProductFilterDto> filters;

    public RequestFilterDto() {
        this.filters = new ArrayList<>();
    }

    public List<ProductFilterDto> getFilters() {
        return filters;
    }

    public void setFilters(List<ProductFilterDto> filters) {
        this.filters = filters;
    }
}
