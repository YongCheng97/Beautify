package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewExistException;
import util.exception.ReviewNotFoundException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;

public interface ReviewSessionBeanLocal {

    public Review createNewServiceReview(Review newReview, Long customerId, Long bookingId) throws ReviewExistException, UnknownPersistenceException, InputDataValidationException, CreateNewReviewException;

    public Review createNewProductReview(Review newReview, Long customerId, Long purchasedLineItemId) throws ReviewExistException, UnknownPersistenceException, InputDataValidationException, CreateNewReviewException;

    public List<Review> retrieveAllReviews();

    public Review retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public List<Review> retrieveServiceReviewsByServiceProviderId(Long serviceProviderId) throws ServiceProviderNotFoundException;

    public List<Review> retrieveProductReviewsByServiceProviderId(Long serviceProviderId);

    public void updateReview(Review review) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException;

    public void deleteReview(Long reviewId) throws ReviewNotFoundException;

}
