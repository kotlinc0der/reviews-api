package com.udacity.course3.reviews.service;

import com.udacity.course3.reviews.exception.ReviewNotFoundException;
import com.udacity.course3.reviews.model.document.ReviewDoc;
import com.udacity.course3.reviews.model.entity.Product;
import com.udacity.course3.reviews.model.entity.Review;
import com.udacity.course3.reviews.repository.ReviewsMongoRepository;
import com.udacity.course3.reviews.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for working with review entity.
 */
@Service
public class ReviewsService {

    private ReviewsRepository reviewsRepository;
    private ReviewsMongoRepository reviewsMongoRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity.
     * 2. Check for existence of product.
     * 3. If product not found, throw Exception.
     * 4. If found, save review.
     *
     * @param product The product.
     * @return The created review or throw exception if product id is not found.
     */
    public Review createReviewForProduct(Product product, Review review) {
        review.setProduct(product);
        review.setProductId(product.getId());
        review.setComments(review.getComments());
        review.setCreatedDate(review.getCreatedDate() != null ? review.getCreatedDate() : new Date());
        review.setRating(formatRating(review.getRating()));
        Review savedReview = reviewsRepository.save(review);
        savedReview.getComments().forEach(comment -> comment.setReviewId(savedReview.getId()));
        reviewsMongoRepository.save(ReviewDoc.fromReview(review));
        return savedReview;
    }

    /**
     * Lists reviews by product.
     *
     * @param product The product.
     * @return The list of reviews.
     */
    public List<ReviewDoc> listReviewsForProduct(Product product) {
        Optional<List<Review>> reviewOptionals = reviewsRepository.findAllByProduct(product);
        List<Review> reviews = reviewOptionals.orElse(new ArrayList<>());
        List<Long> reviewIds = reviews.stream().map(Review::getId).collect(Collectors.toList());
        Optional<List<ReviewDoc>> reviewDocs = reviewsMongoRepository.findReviewDocsByReviewEntityIdIn(reviewIds);
        return reviewDocs.orElse(new ArrayList<>());
    }

    /**
     * Finds a review by id.
     *
     * @param id The id of the review.
     * @return The review if found, or throws exception.
     */
    public Review findById(Long id) {
        Optional<Review> review = reviewsRepository.findById(id);
        if (!review.isPresent()) {
            throw new ReviewNotFoundException(id);
        }
        return review.get();
    }

    private static Double formatRating(Double reviewRating) {
        DecimalFormat instance = new DecimalFormat();
        instance.setRoundingMode(RoundingMode.FLOOR);
        instance.setMaximumIntegerDigits(2);
        instance.setMaximumFractionDigits(2);
        return Double.valueOf(instance.format(reviewRating));
    }

    @Autowired
    public void setReviewsRepository(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Autowired
    public void setReviewsMongoRepository(ReviewsMongoRepository reviewsMongoRepository) {
        this.reviewsMongoRepository = reviewsMongoRepository;
    }
}