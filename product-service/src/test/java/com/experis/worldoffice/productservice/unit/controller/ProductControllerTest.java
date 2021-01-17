package com.experis.worldoffice.productservice.unit.controller;

import com.experis.worldoffice.productservice.controller.ProductController;
import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private MvcResult mvcResult;
    private ObjectMapper om;

    public ProductControllerTest() {
        this.rand = new Random();
        this.productDtos = new ArrayList<>();
        this.om=new ObjectMapper();
    }

    private void thenIndexResultHasAnArrayInContentProperty() throws UnsupportedEncodingException, JsonProcessingException {
        String response = this.mvcResult.getResponse().getContentAsString();
        Map<String,Object> products = om.readValue(response,Map.class);
            assertTrue(products.get("content") instanceof ArrayList);
    }

    private void thenIndexResponseOk()
        throws Exception {

        this.mvcResult = this.resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();
    }


    @Test
    void index() throws Exception {
        this.givenValidProductList();
        this.givenValidProductListPaginated();
        this.whenMvcMakeRequestToIndex();
        this.thenIndexResponseOk();
        this.thenIndexResultHasAnArrayInContentProperty();

    }
//
//    @Test
//    void currentExistence() {
//    }
//
//    @Test
//    void indexFiltered() {
//    }
//
//    @Test
//    void decreaseStock() {
//    }

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
            productDtos.subList(initialIndex, initialIndex + size ),
            this.pageable,
            productDtos.size()
        );
        given(service.findAll(any(Pageable.class))).willReturn(pageProducts);
    }

    private void whenMvcMakeRequestToIndex() throws Exception {
        this.resultActions = mvc.perform(get("/product/")
            .param("page",String.valueOf(page))
            .param("size",String.valueOf(size))
            .contentType(MediaType.APPLICATION_JSON));
    }

}