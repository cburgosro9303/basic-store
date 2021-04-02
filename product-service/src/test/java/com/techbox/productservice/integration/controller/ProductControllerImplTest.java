package com.techbox.productservice.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbox.productservice.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerImplTest {

    @Autowired
    private MockMvc mvc;
    private long productId;
    private AlterStockDto alterStockDto;
    private static final int MAX_RESULTS = 10;
    private static final int MAX_PAGE = 2;
    private Pageable pageable;
    private int page;
    private int size;
    private Random rand;
    private ResultActions resultActions;
    private RequestFilterDto requestFilterDto;
    private MvcResult mvcResult;
    private ObjectMapper om;

    public ProductControllerImplTest() {
        this.rand = new Random();
        this.om = new ObjectMapper();
    }


    @Test
    void index() throws Exception {
        givenValidPageable();
        whenMakeRequestToIndex();
        assertAll(() -> {
            thenReturnSuccessOperation();
            thenReturnSuccessOperationWithArray();
        });
    }

    @Test
    void validCurrentExistence() throws Exception {
        givenValidProductId();
        whenMakeRequestToCurrentExistence();
        assertAll(() -> {
            thenReturnSuccessOperation();
            thenReturnSuccessOperationWithCurrentExistenceGtZero();
        });
    }

    @Test
    void invalidCurrentExistence() throws Exception {
        givenInvalidProductId();
        whenMakeRequestToCurrentExistence();
        assertAll(() -> {
            thenReturnSuccessOperation();
            thenReturnSuccessOperationWithCurrentExistenceEqZero();
        });
    }

    @Test
    void decreaseStock() throws Exception {
        givenValidProductId();
        givenValidAlterStockDto();
        whenMakeRequestToDecreaseStock();
        assertAll(() -> {
            thenReturnSuccessOperation();
        });
    }

    @Test
    void decreaseInvalidStockQuantity() throws Exception {
        givenValidProductId();
        givenInvalidAlterStockDto();
        whenMakeRequestToDecreaseStock();
        assertAll(() -> {
            thenReturnConflictOperation();
        });
    }



    @Test
    void increaseStock() throws Exception {
        givenValidProductId();
        givenValidAlterStockDto();
        whenMakeRequestToIncreaseStock();
        assertAll(() -> {
            thenReturnSuccessOperation();
        });
    }

    @Test
    void filteredRequest() throws Exception {
        givenValidPageable();
        givenValidRequestFilterDto();
        whenMakeRequestToIndexFiltered();
        assertAll(() -> {
            thenReturnSuccessOperation();
            thenReturnSuccessOperationWithArray();
        });
    }

    private void givenValidRequestFilterDto() {
        requestFilterDto = new RequestFilterDto();
        ProductFilterDto productFilterDto = new ProductFilterDto();
        productFilterDto.setProductFieldEmun(ProductFieldEmun.NAME);
        productFilterDto.setFilterTypeEmun(FilterTypeEmun.EQ);
        productFilterDto.setValue("Iphone 6");
        requestFilterDto.getFilters().add(productFilterDto);
    }


    private void givenValidProductId() {
        this.productId = 1;
    }

    private void givenInvalidProductId() {
        this.productId = 100;
    }

    private void givenValidPage() {
        int int_random = rand.nextInt(MAX_PAGE);
        this.page = int_random;
    }

    private void givenValidAlterStockDto() {
        alterStockDto = new AlterStockDto();
        alterStockDto.setProductId(productId);
        alterStockDto.setOperationValue(5L);
    }

    private void givenInvalidAlterStockDto() {
        alterStockDto = new AlterStockDto();
        alterStockDto.setProductId(productId);
        alterStockDto.setOperationValue(500L);
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

    private void whenMakeRequestToIndexFiltered() throws Exception {
        this.mvcResult = mvc.perform(post("/product/")
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .content(this.om.writeValueAsString(requestFilterDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();
    }


    private void whenMakeRequestToCurrentExistence() throws Exception {
        this.mvcResult = mvc.perform(get("/product/existence/")
            .param("productId", String.valueOf(this.productId))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();
    }

    private void whenMakeRequestToDecreaseStock() throws Exception {
        this.mvcResult = mvc.perform(post("/product/decreaseStock/")
            .content(this.om.writeValueAsString(alterStockDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();
    }

    private void whenMakeRequestToIncreaseStock() throws Exception {
        this.mvcResult = mvc.perform(post("/product/increaseStock/")
            .content(this.om.writeValueAsString(alterStockDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();
    }

    private void thenReturnSuccessOperation() throws UnsupportedEncodingException, JsonProcessingException {
        assertEquals(HttpStatus.OK.value(), this.mvcResult.getResponse().getStatus());
    }

    private void thenReturnConflictOperation() {
        assertEquals(HttpStatus.CONFLICT.value(), this.mvcResult.getResponse().getStatus());
    }

    private void thenReturnSuccessOperationWithArray() throws UnsupportedEncodingException, JsonProcessingException {
        String response = this.mvcResult.getResponse().getContentAsString();
        Map<String, Object> products = om.readValue(response, Map.class);
        assertTrue(products.get("content") instanceof ArrayList);
    }

    private void thenReturnSuccessOperationWithCurrentExistenceGtZero() throws UnsupportedEncodingException, JsonProcessingException {
        String response = this.mvcResult.getResponse().getContentAsString();
        CurrentExistenceDto currentExistenceDto = om.readValue(response, CurrentExistenceDto.class);

        assertAll(() -> {
            assertNotNull(currentExistenceDto);
            assertNotNull(currentExistenceDto.getCurrentExistence());
            assertTrue(currentExistenceDto.getCurrentExistence() > 0);
        });
    }

    private void thenReturnSuccessOperationWithCurrentExistenceEqZero() throws UnsupportedEncodingException, JsonProcessingException {
        String response = this.mvcResult.getResponse().getContentAsString();
        CurrentExistenceDto currentExistenceDto = om.readValue(response, CurrentExistenceDto.class);

        assertAll(() -> {
            assertNotNull(currentExistenceDto);
            assertNotNull(currentExistenceDto.getCurrentExistence());
            assertEquals(0L, (long) currentExistenceDto.getCurrentExistence());
        });
    }
}