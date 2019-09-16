package com.udacity.course3.reviews.service;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with comment entity.
 */
@Service
public class CommentsService {
    private CommentsRepository commentsService;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity.
     * 2. Check for existence of review.
     * 3. If review not found, throw Exception.
     * 4. If found, save comment.
     *
     * @param comment The comment to save.
     * @param review The review that the comment is created for
     */
    public Comment createCommentForReview(Comment comment, Review review) {
        comment.setReview(review);
        comment.setReviewId(review.getId());
        return commentsService.save(comment);
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, throw Exception.
     * 4. If found, return list of comments.
     *
     * @param review The review.
     */
    public List<Comment> listCommentsForReview(Review review) {
        return review.getComments();
    }

    @Autowired
    public void setCommentsService(CommentsRepository commentsService) {
        this.commentsService = commentsService;
    }
}