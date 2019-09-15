package com.udacity.course3.reviews.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {}

    public ProductNotFoundException(Long productId) {
        super(String.format("Product with id = %s was not found", productId));
    }
}
