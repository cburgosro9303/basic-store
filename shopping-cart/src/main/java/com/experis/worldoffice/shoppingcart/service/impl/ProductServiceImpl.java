package com.experis.worldoffice.shoppingcart.service.impl;

import com.experis.worldoffice.shoppingcart.dto.CurrentExistenceDto;
import com.experis.worldoffice.shoppingcart.dto.AlterStockDto;
import com.experis.worldoffice.shoppingcart.exception.FailedRequestProductException;
import com.experis.worldoffice.shoppingcart.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG= LogManager.getLogger(ProductServiceImpl.class);
    private RestTemplate restTemplate;
    private String productServicePath;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.productServicePath = "http://localhost:8080/experis-product/product";
    }

    @Override
    public Long getCurrentExistence(Long productId) throws FailedRequestProductException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.productServicePath)
            .queryParam("productId", productId);

        ResponseEntity<CurrentExistenceDto> response = restTemplate.getForEntity(builder.toUriString(),CurrentExistenceDto.class);
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody().getCurrentExistence();
        }
        else{
            LOG.error("Product Service is failing. Response code {}\n response {}",response.getStatusCode(),response);
            throw new FailedRequestProductException();
        }
    }

    @Override
    public Boolean decreaseProductStock(Long productId) throws InstantiationException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.productServicePath + "/decreaseStock");
        HttpEntity<AlterStockDto> request = new HttpEntity<>(new AlterStockDto(productId,1L));
        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(builder.toUriString(), request, Boolean.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return Boolean.TRUE;
            }
            else{
                LOG.error("Product Service is failing. Response code {}\n response {}",response.getStatusCode(),response);
                throw new InstantiationException();
            }
        }catch (RuntimeException e){
            LOG.error("Error in request {}",request,e);
            return Boolean.FALSE;
        }

    }

    @Override
    public void increaseProductStock(Long productId)  {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.productServicePath + "/increaseStock");
        HttpEntity<AlterStockDto> request = new HttpEntity<>(new AlterStockDto(productId,1L));
        restTemplate.postForEntity(builder.toUriString(),request,Boolean.class);
    }
}
