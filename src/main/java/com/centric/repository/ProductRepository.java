package com.centric.repository;

import com.centric.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {

    @Query(value = "SELECT * FROM products " +
                   "WHERE category = :category ORDER BY created_date DESC " +
                   "LIMIT :limit OFFSET :offset ", nativeQuery = true)
    List<Product> findProducts(@Param("category") String category,
                               @Param("limit") Integer limit,
                               @Param("offset") Integer offset);

    @Query(value = "SELECT COUNT(*) FROM products WHERE category = :category", nativeQuery = true)
    Integer getProductsCount(@Param("category") String category);
}
