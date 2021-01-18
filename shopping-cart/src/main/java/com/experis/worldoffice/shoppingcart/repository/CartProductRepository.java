package com.experis.worldoffice.shoppingcart.repository;

import com.experis.worldoffice.shoppingcart.model.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CartProductRepository extends PagingAndSortingRepository<CartProduct,Long> {


    @Query("Select cp from CartProduct cp join cp.shoppingCart sc where sc.purchaseId=:purchaseId")
    Page<CartProduct> findAllByPurchaseId(@Param("purchaseId")Long purchaseId, Pageable pageable);
}
