package com.experis.worldoffice.batchservice.job.experis;


import com.experis.worldoffice.batchservice.dto.ProductDto;
import com.experis.worldoffice.batchservice.model.entity.Brand;
import com.experis.worldoffice.batchservice.model.entity.Product;
import com.experis.worldoffice.batchservice.model.entity.State;
import com.experis.worldoffice.batchservice.service.BrandService;
import com.experis.worldoffice.batchservice.service.StateService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Scope("step")
@Component("dataItemProcessor")
public class ProductProcessor implements ItemProcessor<ProductDto, Product> {

    @Autowired
    private BrandService brandService;

    @Autowired
    private StateService stateService;

    public Product process(ProductDto productDto) {

        if (Objects.isNull(productDto) || Objects.isNull(productDto.getName()) || Objects.isNull(productDto.getBrand())
            || Objects.isNull(productDto.getState()) || Objects.isNull(productDto.getDiscount()) || Objects.isNull(productDto.getPrice())
            || Objects.isNull(productDto.getStock())) {
            return null;
        }
        Brand brand = brandService.findBrandByName(productDto.getBrand());
        State state = stateService.findStateByName(productDto.getState());
        if (Objects.isNull(brand) || Objects.isNull(state)) {
            return null;
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(new BigDecimal(productDto.getPrice()));
        product.setDiscount((float) productDto.getDiscount() / 100);
        product.setStock(productDto.getStock());
        product.setBrand(brand);
        product.setState(state);
        return product;
    }
}