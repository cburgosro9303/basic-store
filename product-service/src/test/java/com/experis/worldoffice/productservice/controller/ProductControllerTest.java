package com.experis.worldoffice.productservice.controller;

import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    private static final int MAX_RESULTS = 5;
    private static final int MAX_PAGE = 2;
    private Page<ProductDto> pageProducts;
    private Pageable pageable;
    private int page;
    private int size;
    private List<ProductDto> productDtos;
    private Random rand;
    private ResultActions resultActions;


    public ProductControllerTest() {
        this.rand = new Random();
        this.productDtos = new ArrayList<>();
    }

    private void givenValidPage() {
        int int_random = rand.nextInt(MAX_PAGE);
        this.page = int_random;
    }

    private void givenValidSize() {
        int int_random = 1 + rand.nextInt(MAX_RESULTS);
        this.size = int_random;
    }

    private void givenInvalidSize() {

    }

    private void givenValidPageable() {
        this.givenValidPage();
        this.givenValidSize();
        pageable = PageRequest.of(this.page, this.size);
    }

    private void givenValidProductList() {
        for (int i = 0; i < 10; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setId(Long.valueOf(i));
            productDtos.add(productDto);
        }
    }

    private void givenValidProductListPaginated() {
        this.givenValidPageable();
        int initialIndex = page * size;
        pageProducts = new PageImpl<>(
            productDtos.subList(initialIndex, initialIndex + (size - 1)),
            this.pageable,
            productDtos.size()
        );
        given(service.findAll(any(Pageable.class))).willReturn(pageProducts);
    }

    private void whenMvcMakeRequestToIndex() throws Exception {
        this.resultActions = mvc.perform(get("/product/")
            .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void thenIndexResponseOk()
        throws Exception {
        this.resultActions.andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }


    @Test
    void index() throws Exception {
        this.givenValidProductList();
        this.givenValidProductListPaginated();
        this.whenMvcMakeRequestToIndex();
        this.thenIndexResponseOk();
    }

    @Test
    void currentExistence() {
    }

    @Test
    void indexFiltered() {
    }

    @Test
    void decreaseStock() {
    }
}