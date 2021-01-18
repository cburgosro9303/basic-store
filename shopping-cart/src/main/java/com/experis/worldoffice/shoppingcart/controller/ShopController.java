package com.experis.worldoffice.shoppingcart.controller;

import com.experis.worldoffice.shoppingcart.dto.CartProductDto;
import com.experis.worldoffice.shoppingcart.dto.ProductDto;
import com.experis.worldoffice.shoppingcart.dto.ShoppingCartDto;
import com.experis.worldoffice.shoppingcart.exception.BuyClosedException;
import com.experis.worldoffice.shoppingcart.exception.FailedRequestProductException;
import com.experis.worldoffice.shoppingcart.exception.InsufficientStockException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Response to Shop activity
 */
@Tag(name = "Shop Controller", description = "Manage shop request")
public interface ShopController {

    /**
     * add product to shopping cart
     *
     * @param productDto
     * @param purchaseId
     * @return
     */
    @Operation(summary = "add products to cart",
        description = "Add products to cart when exist or create one and add into")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "ShoppingCartDto", implementation = ShoppingCartDto.class))
    )
    ResponseEntity<ShoppingCartDto> addProduct(@Parameter(description = "product to add to cart") ProductDto productDto,
                                               @Parameter(description = "purchase id of current cart") Long purchaseId) throws BuyClosedException, InsufficientStockException, FailedRequestProductException;

    /**
     * list cart products
     * @param purchaseId
     * @param page
     * @param size
     * @return
     */
    @Operation(summary = "Find all cart products",
        description = "Find all cart products and return a paginated object")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "PageOfCartProductDto", implementation = Page.class))
    )
    ResponseEntity<Page<CartProductDto>> listProduct(@Parameter(description = "purchase id of current cart") Long purchaseId,
                                                     @Parameter(description = "number grater than or equal to 0") int page,
                                                     @Parameter(description = "number grater than to 0") int size) ;

    /**
     * clean cart
     * @param purchaseId
     * @return
     */
    @Operation(summary = "Delete all cart products",
        description = "Delete all cart products when cart exist an has elements")
    @ApiResponse(
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Boolean", implementation = Boolean.class))
    )
    ResponseEntity<Boolean> cleanCart(@Parameter(description = "purchase id of current cart") Long purchaseId);

    @PostMapping("/purchase/{purchaseId}")
    ResponseEntity<ShoppingCartDto> finishBuying(@PathVariable Long purchaseId) throws InstantiationException, InsufficientStockException;
}
