package com.udacity.course3.reviews.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udacity.course3.reviews.model.entity.Comment;
import com.udacity.course3.reviews.model.entity.Review;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "reviews")
public class ReviewDoc {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private Long reviewEntityId;
    private String content;
    private Double rating;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("created_date")
    private Date createdDate;

    private List<CommentDoc> comments = new ArrayList<>();

    public ReviewDoc() {}

    public Long getReviewEntityId() {
        return reviewEntityId;
    }

    public void setReviewEntityId(Long id) {
        this.reviewEntityId = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProductId() {
        return productId;
    }

    private void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<CommentDoc> getComments() {
        return comments;
    }

    public void setComments(List<CommentDoc> comments) {
        comments.forEach(comment -> comment.setReviewEntityId(reviewEntityId));
        this.comments = comments;
    }

    public void addComment(CommentDoc comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    private void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public static ReviewDoc fromReview(Review review) {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setContent(review.getContent());
        reviewDoc.setCreatedDate(review.getCreatedDate());
        reviewDoc.setProductId(review.getProductId());
        reviewDoc.setRating(review.getRating());
        reviewDoc.setReviewEntityId(review.getId());
        List<Comment> comments = review.getComments();
        for (Comment comment : comments) {
            reviewDoc.addComment(CommentDoc.fromComment(comment));
        }
        return reviewDoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
