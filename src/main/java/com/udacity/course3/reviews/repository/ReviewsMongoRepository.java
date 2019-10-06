package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.document.ReviewDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsMongoRepository extends MongoRepository<ReviewDoc, String> {
    Optional<List<ReviewDoc>> findReviewDocsByReviewEntityIdIn(Collection<Long> reviewEntityIds);
    ReviewDoc findReviewDocByReviewEntityId(Long reviewEntityId);
}
