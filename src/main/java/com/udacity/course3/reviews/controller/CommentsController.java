package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.service.CommentsService;
import com.udacity.course3.reviews.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping(value = "/comments")
public class CommentsController {

    private CommentsService commentsService;
    private ReviewsService reviewsService;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Long reviewId, @RequestBody Comment comment) {
        Review review = reviewsService.findById(reviewId);
        comment.setReview(review);
        Comment savedComment = commentsService.createCommentForReview(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Long reviewId) {
        Review review = reviewsService.findById(reviewId);
        return commentsService.listCommentsForReview(review);
    }

    @Autowired
    public void setCommentsService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @Autowired
    public void setReviewsService(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

}