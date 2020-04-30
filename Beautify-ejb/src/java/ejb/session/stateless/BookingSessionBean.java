package ejb.session.stateless;

import entity.Booking;
import entity.Customer;
import entity.Review;
import entity.Service;
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
import util.exception.BookingExistException;
import util.exception.BookingNotFoundException;
import util.exception.CreateNewBookingException;
import util.exception.CreateNewReviewException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ServiceNotFoundException;

import util.exception.UnknownPersistenceException;

@Stateless
@Local(BookingSessionBeanLocal.class)
public class BookingSessionBean implements BookingSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BookingSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Booking createNewBooking(Booking newBooking, Long customerId, Long serviceId) throws BookingExistException, UnknownPersistenceException, InputDataValidationException, CreateNewBookingException, CustomerNotFoundException {
        Set<ConstraintViolation<Booking>> constraintViolations = validator.validate(newBooking);

        if (constraintViolations.isEmpty()) {
            try {
                if (customerId == null) {
                    throw new CreateNewBookingException("A new booking must be associated with a customer");
                }

                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustId(customerId);

                newBooking.setCustomer(customer);
                customer.getBookings().add(newBooking);

                if (serviceId == null) {
                    throw new CreateNewBookingException("A new booking must be associated with a service");
                }

                Service service = serviceSessionBeanLocal.retrieveServiceByServiceId(serviceId);
                newBooking.setService(service);
                service.getBookings().add(newBooking);

                em.persist(newBooking);
                em.flush();

                return newBooking;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new BookingExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CustomerNotFoundException | ServiceNotFoundException ex) {
                throw new CreateNewBookingException("An error has occured while creating the new review: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<Booking> retrieveAllBookings() {
        Query query = em.createQuery("SELECT b FROM Booking b");
        List<Booking> bookings = query.getResultList();

        for (Booking booking : bookings) {
            booking.getCustomer();
            booking.getService();
        }

        return bookings;
    }

    @Override
    public Booking retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException {
        Booking booking = em.find(Booking.class, bookingId);

        if (booking != null) {
            booking.getCustomer();
            booking.getService();

            return booking;
        } else {
            throw new BookingNotFoundException("Booking ID " + bookingId + " does not exist!");
        }
    }

    @Override
    public List<Booking> retrieveAllBookingsByCustomerId(Long customerId) {
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.customer.customerId = :inCustomerId ORDER BY b.dateOfAppointment DESC, b.startTime ASC");
        query.setParameter("inCustomerId", customerId);
        List<Booking> bookings = query.getResultList();

        return bookings;
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking bookingToDelete = em.find(Booking.class, bookingId);
        bookingToDelete.getCustomer().getBookings().remove(bookingToDelete);
        bookingToDelete.getService().getBookings().remove(bookingToDelete);
        if (bookingToDelete.getReview() != null) {
            bookingToDelete.getReview().setBooking(null);
        }
        em.remove(bookingToDelete);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Booking>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
