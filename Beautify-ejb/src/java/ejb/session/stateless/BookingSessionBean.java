package ejb.session.stateless;

import entity.Booking;
import entity.CreditCard;
import entity.Customer;
import entity.Review;
import entity.SalesForUs;
import entity.SalesRecord;
import entity.Service;
import java.math.BigDecimal;
import java.util.Date;
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
import util.exception.CreateNewSalesForUsException;
import util.exception.CreateNewSalesRecordException;
import util.exception.CreditCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ServiceNotFoundException;

import util.exception.UnknownPersistenceException;
import util.exception.UpdateBookingException;

@Stateless
@Local(BookingSessionBeanLocal.class)
public class BookingSessionBean implements BookingSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB
    private SalesRecordSessionBeanLocal salesRecordSessionBeanLocal;

    @EJB
    private SalesForUsSessionBeanLocal salesForUsSessionBeanLocal;

    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BookingSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Booking createNewBooking(Booking newBooking, Long customerId, Long serviceId, Long creditCardId) throws BookingExistException, UnknownPersistenceException, InputDataValidationException, CreateNewBookingException, CustomerNotFoundException {
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

                CreditCard creditcard = creditCardSessionBeanLocal.retrieveCreditCardByCreditCardId(creditCardId);

                newBooking.setCreditCard(creditcard);
                creditcard.getBookings().add(newBooking);

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
            } catch (CustomerNotFoundException | ServiceNotFoundException | CreditCardNotFoundException ex) {
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
    public List<Booking> retrieveAllBookingsByServiceId(Long serviceId) {
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.service.serviceId = :inServiceId ORDER BY b.dateOfAppointment DESC, b.startTime ASC");
        query.setParameter("inServiceId", serviceId);
        List<Booking> bookings = query.getResultList();

        return bookings;
    }

    @Override
    public List<Booking> retrieveAllBookingsByCustomerId(Long customerId) {
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.customer.customerId = :inCustomerId ORDER BY b.dateOfAppointment DESC, b.startTime ASC");
        query.setParameter("inCustomerId", customerId);
        List<Booking> bookings = query.getResultList();

        return bookings;
    }

    @Override
    public List<Booking> retrieveAllBookingsByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.service.serviceProvider.serviceProviderId = :inServiceProviderId ORDER BY b.dateOfAppointment DESC, b.startTime ASC");
        query.setParameter("inServiceProviderId", serviceProviderId);
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

    @Override
    public void updateBookingStatus(Long bookingId, String status) throws BookingNotFoundException, UpdateBookingException, InputDataValidationException {
        if (bookingId != null) {
            Booking booking = retrieveBookingByBookingId(bookingId);
            Set<ConstraintViolation<Booking>> constraintViolations = validator.validate(booking);

            if (constraintViolations.isEmpty()) {
                Booking bookingToUpdate = retrieveBookingByBookingId(booking.getBookingId());

                if (bookingToUpdate.getCustomer().equals(bookingToUpdate.getCustomer())) {
                    bookingToUpdate.setStatus(status);

                    if (status.equals("Completed")) {
                        BigDecimal salesRecordAmt = bookingToUpdate.getPrice().multiply(new BigDecimal("0.95")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        BigDecimal salesForUsAmt = bookingToUpdate.getPrice().multiply(new BigDecimal("0.05")).setScale(2, BigDecimal.ROUND_HALF_EVEN);

                        try {
                            salesRecordSessionBeanLocal.createNewSalesRecordBooking(new SalesRecord(salesRecordAmt, new Date()), bookingToUpdate.getBookingId());
                        } catch (CreateNewSalesRecordException ex) {
                            System.err.println("An error has occured while creating the new sales record: " + ex.getMessage());
                        }

                        try {
                            salesForUsSessionBeanLocal.createNewSalesForUsBooking(new SalesForUs(salesForUsAmt, new Date()), bookingToUpdate.getBookingId());
                        } catch (CreateNewSalesForUsException ex) {
                            System.err.println("An error has occured while creating the new sales for us: " + ex.getMessage());
                        }
                    }
                } else {
                    throw new UpdateBookingException("Customer of booking record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new BookingNotFoundException("Booking ID not provided for booking to be updated");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Booking>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
