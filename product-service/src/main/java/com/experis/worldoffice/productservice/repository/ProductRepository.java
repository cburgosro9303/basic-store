package com.experis.worldoffice.productservice.repository;

import com.experis.worldoffice.productservice.dto.ProductDto;
import com.experis.worldoffice.productservice.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * This method use query method for query build
     *
     * @param name     Name or part of name for filter results
     * @param pageable page and size for new result list
     * @return paginated Product list
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * This method return ProductDto List with pagination data
     *
     * @param brandId  brand id for filter products
     * @param pageable page and size for new result list
     * @return paginated ProductDto list
     */
    @Query("SELECT new com.experis.worldoffice.productservice.dto.ProductDto(p.id, p.name, p.price, p.stock, p.discount," +
        " s.id, s.name, b.id, b.name) FROM Product p join p.brand b join p.state s where b.id= :brandId")
    Page<ProductDto> findAllProductsByBrandId(@Param("brandId") Long brandId, Pageable pageable);

    /**
     * This method return ProductDto List with pagination data
     *
     * @param brandName brand name for filter products
     * @param pageable  page and size for new result list
     * @return paginated ProductDto list
     */
    @Query("SELECT new com.experis.worldoffice.productservice.dto.ProductDto(p.id, p.name, p.price, p.stock, p.discount," +
        " s.id, s.name, b.id, b.name) FROM Product p join p.brand b join p.state s where b.name= :brandName")
    Page<ProductDto> findAllProductsByBrandName(@Param("brandName") String brandName, Pageable pageable);

    /**
     * This method return Product List Objects filter by a price range
     *
     * @param minor    minor price value
     * @param major    major price value
     * @param pageable page and size for new result list
     * @return paginated ProductDto list
     */
    Page<ProductDto> findByPriceBetween(BigDecimal minor, BigDecimal major, Pageable pageable);


    /**
     * This method update product stock quantity
     *
     * @param productId       product to update
     * @param decreseQuantity quantity of decrease stock
     */
    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("Update Product p set p.stock = p.stock - :decreaseValue where p.id = :productId")
    void decreaseProductStock(@Param("productId") Long productId, @Param("decreaseValue") Long decreseQuantity);

    /**
     * This method return current stock for a specific product
     * @param productId product to update
     * @return current stock for selected product
     */
    @Query("select p.stock from Product p where p.id=:productId")
    Optional<Long> getCurrentProductStock(@Param("productId") Long productId);
}
