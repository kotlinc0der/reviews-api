package com.udacity.course3.reviews.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udacity.course3.reviews.model.entity.Comment;

import java.util.Date;

public class CommentDoc {

    private String content;
    @JsonProperty("review_id")
    private Long reviewEntityId;
    @JsonProperty("created_date")
    private Date createdDate;

    public CommentDoc() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReviewEntityId() {
        return reviewEntityId;
    }

    public void setReviewEntityId(Long reviewEntityId) {
        this.reviewEntityId = reviewEntityId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    private void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public static CommentDoc fromComment(Comment comment) {
        CommentDoc commentDoc = new CommentDoc();
        commentDoc.setReviewEntityId(comment.getReviewId());
        commentDoc.setContent(comment.getContent());
        commentDoc.setCreatedDate(comment.getCreatedDate());
        return commentDoc;
    }
}
