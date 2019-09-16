package com.udacity.course3.reviews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {}

    public ReviewNotFoundException(Long reviewId) {
        super(String.format("Review with id = %s was not found", reviewId));
    }
}
