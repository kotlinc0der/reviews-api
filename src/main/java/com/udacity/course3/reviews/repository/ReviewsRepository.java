package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.entity.Product;
import com.udacity.course3.reviews.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByProduct(Product product);
}
