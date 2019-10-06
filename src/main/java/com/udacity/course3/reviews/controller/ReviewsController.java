package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.document.ReviewDoc;
import com.udacity.course3.reviews.model.entity.Product;
import com.udacity.course3.reviews.model.entity.Review;
import com.udacity.course3.reviews.service.ProductsService;
import com.udacity.course3.reviews.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
@RequestMapping(value = "/reviews")
public class ReviewsController {

    // Services wired instead of repositories in order to separate concerns by having the service access data instead of the controller
    private ProductsService productsService;
    private ReviewsService reviewsService;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     * 5. Update average rating of the reviewed product
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Long productId, @Valid @RequestBody Review review) {
        Product product = productsService.findById(productId);
        Review savedReview = reviewsService.createReviewForProduct(product, review);
        productsService.updateProductRating(productId);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Long productId) {
        Product product = productsService.findById(productId);
        List<ReviewDoc> reviews = reviewsService.listReviewsForProduct(product);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setReviewsRepository(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }
}