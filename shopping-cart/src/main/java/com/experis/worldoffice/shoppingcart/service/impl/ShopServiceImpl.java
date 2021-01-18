package com.experis.worldoffice.shoppingcart.service.impl;

import com.experis.worldoffice.shoppingcart.dto.CartProductDto;
import com.experis.worldoffice.shoppingcart.dto.ProductDto;
import com.experis.worldoffice.shoppingcart.dto.ShopStateEnum;
import com.experis.worldoffice.shoppingcart.dto.ShoppingCartDto;
import com.experis.worldoffice.shoppingcart.exception.BuyClosedException;
import com.experis.worldoffice.shoppingcart.exception.FailedRequestProductException;
import com.experis.worldoffice.shoppingcart.exception.InsufficientStockException;
import com.experis.worldoffice.shoppingcart.model.entity.CartProduct;
import com.experis.worldoffice.shoppingcart.model.entity.ShoppingCart;
import com.experis.worldoffice.shoppingcart.repository.CartProductRepository;
import com.experis.worldoffice.shoppingcart.repository.ShoppingCartRepository;
import com.experis.worldoffice.shoppingcart.service.ProductService;
import com.experis.worldoffice.shoppingcart.service.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger LOG = LogManager.getLogger(ShopServiceImpl.class);

    private ProductService productService;
    private ShoppingCartRepository shoppingCartRepository;
    private CartProductRepository cartProductRepository;
    private ObjectMapper om;

    public ShopServiceImpl(ProductService productService, ShoppingCartRepository shoppingCartRepository,
                           CartProductRepository cartProductRepository, ObjectMapper om) {
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartProductRepository = cartProductRepository;
        this.om = om;
    }

    @Override
    public ShoppingCartDto addProduct(ProductDto productDto) throws FailedRequestProductException,
        InsufficientStockException, BuyClosedException {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setPurchaseId(System.nanoTime());
        shoppingCart.setShopStateEnum(ShopStateEnum.IN_PROGRESS);
        shoppingCartRepository.save(shoppingCart);

        return addProduct(productDto, shoppingCart.getPurchaseId());
    }

    @Override
    public ShoppingCartDto addProduct(ProductDto productDto, Long purchaseId) throws FailedRequestProductException,
        InsufficientStockException, BuyClosedException {
        Long existence = productService.getCurrentExistence(productDto.getId());
        if (existence <= 0) {
            throw new InsufficientStockException();
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findByPurchaseId(purchaseId).orElseThrow();
        if (shoppingCart.getShopStateEnum().equals(ShopStateEnum.COMPLETE)) {
            LOG.error("Purchase is closed. purchase id {}", purchaseId);
            throw new BuyClosedException();
        }

        return saveProductAndMapToDto(shoppingCart, productDto);
    }

    private ShoppingCartDto saveProductAndMapToDto(ShoppingCart shoppingCart, ProductDto productDto) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setOriginalProductId(productDto.getId());
        cartProduct.setBrand(productDto.getBrand().getName());
        cartProduct.setDiscount(productDto.getDiscount());
        cartProduct.setPrice(productDto.getPrice());
        cartProduct.setName(productDto.getName());
        cartProduct.setShoppingCart(shoppingCart);
        cartProductRepository.save(cartProduct);

        return om.convertValue(shoppingCartRepository.findById(shoppingCart.getId()), ShoppingCartDto.class);
    }

    @Override
    public Page<CartProductDto> getCurrentProducts(Long purchaseId, Pageable pageable) {

        Page<CartProduct> cartProducts = cartProductRepository.findAllByPurchaseId(purchaseId, pageable);
        return new PageImpl<>(
            cartProducts.getContent().stream()
                .map(e -> om.convertValue(e, CartProductDto.class))
                .collect(Collectors.toList()),
            cartProducts.getPageable(), cartProducts.getTotalElements());

    }

    @Override
    public Boolean cleanShoppingCart(Long purchaseId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByPurchaseId(purchaseId).orElse(null);
        if(Objects.isNull(shoppingCart)){
            return Boolean.FALSE;
        }
        if(shoppingCart.getCartProducts().isEmpty()){
            return Boolean.FALSE;
        }
        cartProductRepository.deleteAll(shoppingCart.getCartProducts());
        return Boolean.TRUE;
    }

    @Override
    public ShoppingCartDto finishBuying(Long purchaseId) throws InstantiationException, InsufficientStockException {
        ShoppingCart shoppingCart = shoppingCartRepository.findByPurchaseId(purchaseId).orElseThrow();
        if(shoppingCart.getShopStateEnum().equals(ShopStateEnum.COMPLETE)){
            return om.convertValue(shoppingCart, ShoppingCartDto.class);
        }
        List<CartProduct> decreasedProducts = new ArrayList<>();
        for (CartProduct product : shoppingCart.getCartProducts()) {
            Boolean decreaseResult = productService.decreaseProductStock(product.getOriginalProductId());
            if(!decreaseResult){
                rollbackDecreasedProducts(decreasedProducts);
                LOG.error("Stock has changed, {} hasn't existence",product);
                throw new InsufficientStockException();
            }
            decreasedProducts.add(product);

        }
        shoppingCart.setShopStateEnum(ShopStateEnum.COMPLETE);
        shoppingCart.setShopDate(new Date());
        shoppingCartRepository.save(shoppingCart);

        return om.convertValue(shoppingCartRepository.findByPurchaseId(purchaseId), ShoppingCartDto.class);
    }

    private void rollbackDecreasedProducts(List<CartProduct> products) throws InstantiationException {
        for (CartProduct product : products) {
            productService.increaseProductStock(product.getOriginalProductId());
        }
    }

}
