package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "update product set average_rating = (SELECT AVG(rating) from review where product_id = ?1) where id = ?1", nativeQuery = true)
    void updateAverageRating(Long productId);
}
