package com.udacity.course3.reviews.service;

import com.udacity.course3.reviews.exception.ProductNotFoundException;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with product entity.
 */
@Service
public class ProductsService {

    private ProductsRepository productsRepository;

    /**
     * Creates a product.
     *
     * 1. Accept product as argument.
     * 2. Save product.
     */
    public void createProduct(Product product) {
        productsRepository.save(product);
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or throws exception.
     */
    public Product findById(Long id) {
        Optional<Product> product = productsRepository.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        return product.get();
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    public List<Product> listProducts() {
        return (List<Product>) productsRepository.findAll();
    }

    /**
     * Update Average Rating of product entity.
     */
    public void updateProductRating(Long productId) {
        productsRepository.updateAverageRating(productId);
    }

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
}