package com.experis.worldoffice.shoppingcart.controller.impl;

import com.experis.worldoffice.shoppingcart.controller.ShopController;
import com.experis.worldoffice.shoppingcart.dto.CartProductDto;
import com.experis.worldoffice.shoppingcart.dto.ProductDto;
import com.experis.worldoffice.shoppingcart.dto.ShoppingCartDto;
import com.experis.worldoffice.shoppingcart.exception.BuyClosedException;
import com.experis.worldoffice.shoppingcart.exception.FailedRequestProductException;
import com.experis.worldoffice.shoppingcart.exception.InsufficientStockException;
import com.experis.worldoffice.shoppingcart.service.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping({"/shop",})
public class ShopControllerImpl implements ShopController {


    private ShopService shopService;

    public ShopControllerImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping({"/addProduct", "/addProduct/{purchaseId}"})
    @Override
    public ResponseEntity<ShoppingCartDto> addProduct(@Valid @RequestBody ProductDto productDto,
                                                      @PathVariable(required = false) Long purchaseId)
        throws BuyClosedException, InsufficientStockException, FailedRequestProductException {
        if (Objects.isNull(purchaseId)) {
            return ResponseEntity.ok(shopService.addProduct(productDto));
        } else {
            return ResponseEntity.ok(shopService.addProduct(productDto, purchaseId));
        }
    }

    @GetMapping("/{purchaseId}")
    @Override
    public ResponseEntity<Page<CartProductDto>> listProduct(@PathVariable Long purchaseId,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(shopService.getCurrentProducts(purchaseId, pageable));
    }

    @DeleteMapping("/cleanCart/{purchaseId}")
    @Override
    public ResponseEntity<Boolean> cleanCart(@PathVariable Long purchaseId) {
        return shopService.cleanShoppingCart(purchaseId) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @PostMapping("/purchase/{purchaseId}")
    @Override
    public ResponseEntity<ShoppingCartDto> finishBuying(@PathVariable Long purchaseId) throws InstantiationException, InsufficientStockException {
        return ResponseEntity.ok(shopService.finishBuying(purchaseId));
    }
}
