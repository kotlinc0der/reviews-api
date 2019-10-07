package com.udacity.course3.reviews.service;

import com.udacity.course3.reviews.model.document.CommentDoc;
import com.udacity.course3.reviews.model.document.ReviewDoc;
import com.udacity.course3.reviews.model.entity.Comment;
import com.udacity.course3.reviews.model.entity.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.ReviewsMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for working with comment entity.
 */
@Service
public class CommentsService {
    private CommentsRepository commentsRepository;
    private ReviewsMongoRepository reviewsMongoRepository;

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
    @Transactional
    public Comment createCommentForReview(Comment comment, Review review) {
        comment.setReview(review);
        comment.setReviewId(review.getId());
        Comment savedCommentEntity = commentsRepository.save(comment);
        ReviewDoc reviewDoc = reviewsMongoRepository.findReviewDocByReviewEntityId(review.getId());
        reviewDoc.addComment(CommentDoc.fromComment(comment));
        reviewsMongoRepository.save(reviewDoc);
        return savedCommentEntity;
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
    public void setCommentsRepository(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Autowired
    public void setReviewsMongoRepository(ReviewsMongoRepository reviewsMongoRepository) {
        this.reviewsMongoRepository = reviewsMongoRepository;
    }
}