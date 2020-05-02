package ejb.session.stateless;

import entity.Customer;
import entity.Product;
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
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ProductNotFoundException;
import util.exception.ServiceNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;
import util.security.CryptographicHelper;

@Stateless
@Local(CustomerSessionBeanLocal.class)

public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CustomerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(newCustomer);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newCustomer);
                em.flush();

                return newCustomer.getCustomerId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CustomerExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public Customer retrieveCustomerByCustId(Long custId) throws CustomerNotFoundException {
        Customer customer = em.find(Customer.class, custId);

        if (customer != null) {
            customer.getCreditCards().size();

            return customer;
        } else {
            throw new CustomerNotFoundException("Customer ID " + custId + " does not exist!");
        }
    }

    @Override
    public Customer retrieveCustomerByCustomerUser(String username) {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        return (Customer) query.getSingleResult();
    }

    @Override
    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT c FROM Customer c");

        return query.getResultList();
    }

    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException {
        Customer customer = retrieveCustomerByCustomerUser(username);
        String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + customer.getSalt()));
        if (customer.getPassword().equals(passwordHash)) {
            customer.getCreditCards().size();

            return customer;
        } else {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateCustomerDetails(Customer customer) throws CustomerNotFoundException, UpdateCustomerException, InputDataValidationException {
        if (customer.getCustomerId() != null && customer != null) {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

            if (constraintViolations.isEmpty()) {
                Customer customerToUpdate = retrieveCustomerByCustId(customer.getCustomerId());

                if (customerToUpdate.getUsername().equals(customerToUpdate.getUsername())) {
                    customerToUpdate.setContactNum(customer.getContactNum());
                    customerToUpdate.setEmail(customer.getEmail());
                    customerToUpdate.setFirstName(customer.getFirstName());
                    customerToUpdate.setLastName(customer.getLastName());
                    customerToUpdate.setUsername(customer.getUsername());
                } else {
                    throw new UpdateCustomerException("Username of customer record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new CustomerNotFoundException("Customer ID not provided for customer to be updated");
        }
    }

    @Override
    public void updateCustomerPassword(Long customerId, String password) throws CustomerNotFoundException, UpdateCustomerException, InputDataValidationException {
        if (customerId != null) {

            Customer customerToUpdate = retrieveCustomerByCustId(customerId);

            if (customerToUpdate.getUsername().equals(customerToUpdate.getUsername())) {
                customerToUpdate.setPassword(password);
            } else {
                throw new UpdateCustomerException("Username of customer record to be updated does not match the existing record");
            }

        } else {
            throw new CustomerNotFoundException("Customer ID not provided for customer to be updated");
        }
    }
    
    @Override 
    public void addFavouriteProduct(Long customerId, Long favouriteProductId) throws CustomerNotFoundException, ProductNotFoundException  {
        if (customerId != null) {
            
            Customer customerToAddFav = retrieveCustomerByCustId(customerId);
            
            Product product = productSessionBeanLocal.retrieveProductByProdId(favouriteProductId);
            
            product.getFavouritedCustomers().add(customerToAddFav);
            customerToAddFav.getFavouriteProducts().add(product);
        }
    }
        
    @Override 
    public void addFavouriteService(Long customerId, Long serviceId) throws CustomerNotFoundException, ServiceNotFoundException  {
        if (customerId != null) {
            
            Customer customerToAddFav = retrieveCustomerByCustId(customerId);
            
            Service service = serviceSessionBeanLocal.retrieveServiceByServiceId(serviceId);
            
            service.getFavouritedCustomers().add(customerToAddFav);
            customerToAddFav.getFavouriteServices().add(service);
        }
    }
    
    @Override 
    public void removeFavouriteProduct(Long customerId, Long favouriteProductId) throws CustomerNotFoundException, ProductNotFoundException  {
        if (customerId != null) {
            
            Customer customerToAddFav = retrieveCustomerByCustId(customerId);
            
            Product product = productSessionBeanLocal.retrieveProductByProdId(favouriteProductId);
            
            product.getFavouritedCustomers().remove(customerToAddFav);
            customerToAddFav.getFavouriteProducts().remove(product);
        }
    }
        
    @Override 
    public void removeFavouriteService(Long customerId, Long serviceId) throws CustomerNotFoundException, ServiceNotFoundException  {
        if (customerId != null) {
            
            Customer customerToAddFav = retrieveCustomerByCustId(customerId);
            
            Service service = serviceSessionBeanLocal.retrieveServiceByServiceId(serviceId);
            
            service.getFavouritedCustomers().remove(customerToAddFav);
            customerToAddFav.getFavouriteServices().remove(service);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Customer>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
