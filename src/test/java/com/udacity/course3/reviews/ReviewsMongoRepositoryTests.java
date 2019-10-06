package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.document.CommentDoc;
import com.udacity.course3.reviews.model.document.ReviewDoc;
import com.udacity.course3.reviews.repository.ReviewsMongoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewsMongoRepositoryTests {

	private static final double RATING = 5.77;
	private static final String TEST_REVIEW = "Test Review";
	private static final String TEST_COMMENT = "Test Comment";


	@Autowired
	private ReviewsMongoRepository reviewsRepository;

	@Before
	public void setUp(){
		reviewsRepository.save(getReview(1L));
		reviewsRepository.save(getReview(2L));
	}

	@After
	public void reset(){
		reviewsRepository.deleteAll();
	}

	@Test
	public void whenFindAllReviewsByReviewIds_thenReturnReviewList() {
		List<Long> reviewIds = Arrays.asList(1L, 2L);
		List<ReviewDoc> reviewDocs = reviewsRepository.findReviewDocsByReviewEntityIdIn(reviewIds).orElse(new ArrayList<>());
		assertThat(reviewDocs).hasSize(2);
	}

	@Test
	public void whenFindReviewById_thenReturnReviewWithComments() {
		long reviewId = 1L;
		ReviewDoc reviewDoc = reviewsRepository.findReviewDocByReviewEntityId(reviewId);
		assertThat(reviewDoc.getReviewEntityId()).isEqualTo(1L);
		assertThat(reviewDoc.getContent()).isEqualTo(TEST_REVIEW);
		assertThat(reviewDoc.getRating()).isEqualTo(RATING);

		List<CommentDoc> comments = reviewDoc.getComments();
		assertThat(comments).hasSize(1);
		assertThat(comments.get(0).getContent()).isEqualTo(TEST_COMMENT);
		assertThat(comments.get(0).getReviewEntityId()).isEqualTo(reviewId);
	}

	private CommentDoc getComment(ReviewDoc review) {
		CommentDoc comment = new CommentDoc();
		comment.setReviewEntityId(review.getReviewEntityId());
		comment.setContent(TEST_COMMENT);
		return comment;
	}

	private ReviewDoc getReview(long reviewEntityId) {
		ReviewDoc review = new ReviewDoc();
		review.setRating(RATING);
		review.setReviewEntityId(reviewEntityId);
		review.setContent(TEST_REVIEW);
		review.setComments(Collections.singletonList(getComment(review)));
		return review;
	}
}