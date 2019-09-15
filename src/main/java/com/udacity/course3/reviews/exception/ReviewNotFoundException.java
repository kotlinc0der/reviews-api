package com.udacity.course3.reviews.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {}

    public ReviewNotFoundException(Long reviewId) {
        super(String.format("Review with id = %s was not found", reviewId));
    }
}
