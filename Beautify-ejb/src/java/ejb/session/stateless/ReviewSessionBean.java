package ejb.session.stateless;

import entity.Booking;
import entity.Customer;
import entity.Product;
import entity.PurchasedLineItem;
import entity.Review;
import entity.Service;
import entity.ServiceProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.BookingNotFoundException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.ReviewExistException;
import util.exception.ReviewNotFoundException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;

@Stateless
@Local(ReviewSessionBeanLocal.class)
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Review createNewServiceReview(Review newReview, Long customerId, Long bookingId) throws ReviewExistException, UnknownPersistenceException, InputDataValidationException, CreateNewReviewException {

        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(newReview);

        if (constraintViolations.isEmpty()) {
            try {
                if (customerId == null) {
                    throw new CreateNewReviewException("A new review must be associated with a customer");
                }

                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustId(customerId);

                newReview.setCustomer(customer);
                customer.getReviews().add(newReview);

                if (bookingId == null) {
                    throw new CreateNewReviewException("A new review must be associated with a booking");
                }

                Booking booking = bookingSessionBeanLocal.retrieveBookingByBookingId(bookingId);

                newReview.setBooking(booking);
                booking.setReview(newReview);

                em.persist(newReview);
                em.flush();

                return newReview;

            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new ReviewExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CustomerNotFoundException | BookingNotFoundException ex) {
                throw new CreateNewReviewException("An error has occured while creating the new review: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public Review createNewProductReview(Review newReview, Long customerId, Long purchasedLineItemId) throws ReviewExistException, UnknownPersistenceException, InputDataValidationException, CreateNewReviewException {

        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(newReview);

        if (constraintViolations.isEmpty()) {
            try {
                if (customerId == null) {
                    throw new CreateNewReviewException("A new review must be associated with a customer");
                }

                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustId(customerId);

                newReview.setCustomer(customer);
                customer.getReviews().add(newReview);

                if (purchasedLineItemId == null) {
                    throw new CreateNewReviewException("A new review must be associated with a purchase");
                }

                PurchasedLineItem purchasedLineItem = purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItemId);

                newReview.setPurchasedLineItem(purchasedLineItem);
                purchasedLineItem.setReview(newReview);

                em.persist(newReview);
                em.flush();

                return newReview;

            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new ReviewExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CustomerNotFoundException | PurchasedLineItemNotFoundException ex) {
                throw new CreateNewReviewException("An error has occured while creating the new review: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<Review> retrieveAllReviews() {
        Query query = em.createQuery("SELECT r FROM Review r ORDER BY r.rating DESC");
        List<Review> reviews = query.getResultList();

        for (Review review : reviews) {
            review.getBooking();
            review.getCustomer();
        }

        return reviews;
    }

    @Override
    public Review retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException {
        Review review = em.find(Review.class, reviewId);

        if (review != null) {
            review.getCustomer();
            review.getBooking();

            return review;
        } else {
            throw new ReviewNotFoundException("Review ID " + reviewId + " does not exist!");
        }
    }

    @Override
    public List<Review> retrieveServiceReviewsByServiceProviderId(Long serviceProviderId) throws ServiceProviderNotFoundException {
        ServiceProvider serviceProvider = em.find(ServiceProvider.class, serviceProviderId);

        if (serviceProvider != null) {
            serviceProvider.getServices().size();
            List<Service> services = serviceProvider.getServices();
            List<Review> reviews = new ArrayList<>();
            for (Service service : services) {
                service.getBookings().size();
                List<Booking> bookings = service.getBookings();
                for (Booking booking : bookings) {
                    Review review = booking.getReview();
                    reviews.add(review);
                }
            }
            return reviews;
        } else {
            throw new ServiceProviderNotFoundException("Service Provider ID " + serviceProviderId + " does not exist!");
        }
    }

    @Override
    public List<Review> retrieveProductReviewsByServiceProviderId(Long serviceProviderId) {
        List<Review> retrievedReviews = new ArrayList<>();
        List<Review> reviews = retrieveAllReviews();

        for (Review review : reviews) {
            Long providerId = review.getPurchasedLineItem().getProduct().getServiceProvider().getServiceProviderId();
            if (providerId != null) {
                if (providerId == serviceProviderId) {
                    retrievedReviews.add(review);
                }
            }
        }
        return retrievedReviews;
    }

    @Override
    public void updateReview(Review review) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException {
        if (review != null && review.getReviewId() != null) {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);

            if (constraintViolations.isEmpty()) {
                Review reviewToUpdate = retrieveReviewByReviewId(review.getReviewId());

                reviewToUpdate.setDescription(review.getDescription());
                reviewToUpdate.setRating(review.getRating());
                reviewToUpdate.setPhoto(review.getPhoto());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Review ID not provided for review to be updated");
        }
    }

    @Override
    public void deleteReview(Long reviewId) throws ReviewNotFoundException {
        Review reviewToRemove = retrieveReviewByReviewId(reviewId);
        Customer customer = reviewToRemove.getCustomer();
        customer.getReviews().remove(reviewToRemove);
        
        if (reviewToRemove.getBooking() != null){
            Booking booking = reviewToRemove.getBooking();
            booking.setReview(null);
        }
        
        if (reviewToRemove.getPurchasedLineItem() != null){
            PurchasedLineItem purchasedLineItem = reviewToRemove.getPurchasedLineItem();
            purchasedLineItem.setReview(null);
        }

        em.remove(reviewToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Review>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
