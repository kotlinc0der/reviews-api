package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.ProductsRepository;
import com.udacity.course3.reviews.repository.ReviewsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewsApplicationTests {

	private static final String TEST_PRODUCT = "Test Product";
	private static final String TEST_DESCRIPTION = "Test Description";
	private static final String TEST_PRICE = "2.50";
	private static final double RATING = 5.77;
	private static final String TEST_REVIEW = "Test Review";
	private static final String TEST_COMMENT = "Test Comment";

	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private ReviewsRepository reviewsRepository;
	@Autowired
	private CommentsRepository commentsRepository;

	@Before
	public void setUp(){
		Product product = testEntityManager.persist(getProduct());
		Review review = testEntityManager.persistFlushFind(getReview(product));
		commentsRepository.save(getComment(review));
	}

	@Test
	public void whenFindProductById_thenReturnProduct() {
		Product product = productsRepository.findById(1L).get();
		assertThat(product.getName()).isEqualTo(TEST_PRODUCT);
		assertThat(product.getPrice()).isEqualTo(new BigDecimal(TEST_PRICE));
		assertThat(product.getDescription()).isEqualTo(TEST_DESCRIPTION);
	}

	@Test
	public void whenFindAllProducts_thenReturnProductList() {
		List<Product> products = productsRepository.findAll();
		assertThat(products).hasSize(1);
	}

	@Test
	public void whenSaveProduct_thenReturnProduct() {
		Product product = productsRepository.save(getProduct());
		assertThat(product.getId()).isEqualTo(2L);
		assertThat(product.getName()).isEqualTo(TEST_PRODUCT);
		assertThat(product.getPrice()).isEqualTo(new BigDecimal(TEST_PRICE));
		assertThat(product.getDescription()).isEqualTo(TEST_DESCRIPTION);
	}

	@Test
	public void whenFindReviewById_thenReturnReview() {
		Review review = reviewsRepository.findById(1L).get();
		assertThat(review.getContent()).isEqualTo(TEST_REVIEW);
		assertThat(review.getProductId()).isEqualTo(1L);
		assertThat(review.getRating()).isEqualTo(RATING);
		assertThat(review.getComments()).hasSize(2);;
	}

	@Test
	public void whenFindAllReviews_thenReturnReviewList() {
		List<Review> reviews = reviewsRepository.findAll();
		assertThat(reviews).hasSize(1);
	}

	@Test
	public void whenSaveReview_thenReturnReview() {
		Product product = productsRepository.findById(1L).get();
		Review review = reviewsRepository.save(getReview(product));
		assertThat(review.getId()).isEqualTo(2L);
		assertThat(review.getContent()).isEqualTo(TEST_REVIEW);
		assertThat(review.getProductId()).isEqualTo(1L);
		assertThat(review.getRating()).isEqualTo(RATING);
		assertThat(review.getComments()).hasSize(1);;
	}

	@Test
	public void whenFindCommentById_thenReturnComment() {
		Comment comment = commentsRepository.findById(1L).get();
		assertThat(comment.getContent()).isEqualTo(TEST_COMMENT);
		assertThat(comment.getReviewId()).isEqualTo(1L);;
	}

	@Test
	public void whenFindAllComments_thenReturnCommentList() {
		List<Comment> comments = commentsRepository.findAll();
		assertThat(comments).hasSize(2);
	}

	@Test
	public void whenSaveComment_thenReturnComment() {
		Review review = reviewsRepository.findById(1L).get();
		Comment comment = commentsRepository.save(getComment(review));
		assertThat(comment.getId()).isEqualTo(3L);
		assertThat(comment.getContent()).isEqualTo(TEST_COMMENT);
		assertThat(comment.getReviewId()).isEqualTo(1L);;
	}


	private Comment getComment(Review review) {
		Comment comment = new Comment();
		comment.setReviewId(review.getId());
		comment.setReview(review);
		comment.setContent(TEST_COMMENT);
		return comment;
	}

	private Review getReview(Product product) {
		Review review = new Review();
		review.setProductId(product.getId());
		review.setProduct(product);
		review.setRating(RATING);
		review.setContent(TEST_REVIEW);
		review.setComments(Collections.singletonList(getComment(review)));
		return review;
	}

	private Product getProduct() {
		Product product = new Product();
		product.setName(TEST_PRODUCT);
		product.setDescription(TEST_DESCRIPTION);
		product.setPrice(new BigDecimal(TEST_PRICE));
		return product;
	}
}