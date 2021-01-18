package com.experis.worldoffice.productservice.controller;

import com.experis.worldoffice.productservice.dto.AlterStockDto;
import com.experis.worldoffice.productservice.dto.CurrentExistenceDto;
import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.dto.RequestFilterDto;
import com.experis.worldoffice.productservice.exception.InsufficientStockException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Response to Product activity
 */
@Tag(name = "Product Controller", description = "Manage product request")
public interface ProductController {

    /**
     * Find all registered products
     *
     * @param page number of page
     * @param size size of page
     * @return Paginated products
     */
    @Operation(summary = "Find all products",
        description = "Find all products and return a paginated object")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Page",implementation = Page.class))
    )
    ResponseEntity<Page<ProductDto>> index(@Parameter(description = "number grater than or equal to 0") int page,
                                           @Parameter(description = "number grater than to 0") int size);

    /**
     * Search product and return their current stock
     *
     * @param productId
     * @return CurrentExistenceDto
     */
    @Operation(summary = "Current Existence",
        description = "Search product by their id and return current existence")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "CurrentExistenceDto",implementation = CurrentExistenceDto.class))
    )
    ResponseEntity<CurrentExistenceDto> currentExistence(@Parameter(description = "Valid product id") Long productId);

    /**
     * Find all registered products with given filters
     *
     * @param page number of page
     * @param size size of page
     * @return Paginated products
     */
    @Operation(summary = "Find all products with given filters",
        description = "Find all products and return a paginated object")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Page",implementation = Page.class))
    )
    ResponseEntity<Page<ProductDto>> indexFiltered(@Parameter(description = "number grater than or equal to 0") int page,
                                    @Parameter(description = "number grater than to 0") int size,
                                    @Parameter(description = "Filters required") RequestFilterDto requestFilter);

    /**
     * decrease stock of a product
     *
     * @param alterStockDto
     * @return Boolean true if success else false
     * @throws InsufficientStockException when decrease value is greater than current product stock
     */
    @Operation(summary = "Decrease Stock",
        description = "Decrease current product existence of given id")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Boolean",implementation = Boolean.class))
    )
    ResponseEntity<Boolean> decreaseStock(AlterStockDto alterStockDto) throws InsufficientStockException;

    /**
     * increase stock of a product
     *
     * @param alterStockDto
     * @return Boolean true if success else false
     * @throws InsufficientStockException when decrease value is greater than current product stock
     */
    @Operation(summary = "Increase Stock",description = "Increase current product existence of given id")
    ResponseEntity<Void> increaseStock(AlterStockDto alterStockDto);


}
