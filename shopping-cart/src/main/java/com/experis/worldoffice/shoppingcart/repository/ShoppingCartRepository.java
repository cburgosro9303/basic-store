package com.experis.worldoffice.shoppingcart.repository;

import com.experis.worldoffice.shoppingcart.model.entity.ShoppingCart;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCart,Long> {

    Optional<ShoppingCart> findByPurchaseId(Long id);
}
