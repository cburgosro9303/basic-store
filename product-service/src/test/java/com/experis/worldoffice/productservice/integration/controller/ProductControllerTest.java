package com.experis.worldoffice.productservice.integration.controller;

import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.service.ProductService;
import com.experis.worldoffice.productservice.controller.ProductController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final int MAX_RESULTS = 10;
    private static final int MAX_PAGE = 2;
    private Pageable pageable;
    private int page;
    private int size;
    private Random rand;
    private ResultActions resultActions;
    private MvcResult mvcResult;
    private ObjectMapper om;

    public ProductControllerTest() {
        this.rand = new Random();
        this.om = new ObjectMapper();
    }


    @Test
    void index() throws Exception {
        givenValidPageable();
        whenMakeRequestToIndex();
        thenReturnSuccesOperation();
    }

    private void givenValidPage() {
        int int_random = rand.nextInt(MAX_PAGE);
        this.page = int_random;
    }

    private void givenValidSize() {
        int int_random = 1 + rand.nextInt(MAX_RESULTS);
        this.size = int_random;
    }

    private void givenValidPageable() {
        this.givenValidPage();
        this.givenValidSize();
        pageable = PageRequest.of(this.page, this.size);
    }

    private void whenMakeRequestToIndex() throws Exception {
        this.mvcResult = mvc.perform(get("/product/")
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();
    }

    private void thenReturnSuccesOperation() throws UnsupportedEncodingException, JsonProcessingException {
        String response = this.mvcResult.getResponse().getContentAsString();
        Map<String, Object> products = om.readValue(response, Map.class);
        assertAll(()->{
            assertEquals(this.mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
            assertTrue(products.get("content") instanceof ArrayList);
        });
    }
}