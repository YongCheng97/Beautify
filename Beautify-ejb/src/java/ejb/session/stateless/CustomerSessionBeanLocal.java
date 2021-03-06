/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ProductNotFoundException;
import util.exception.ServiceNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;

public interface CustomerSessionBeanLocal {

    public Long createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException, InputDataValidationException;

    public Customer retrieveCustomerByCustId(Long custId) throws CustomerNotFoundException;

    public Customer retrieveCustomerByCustomerUser(String username);

    public void updateCustomerDetails(Customer customer) throws CustomerNotFoundException, UpdateCustomerException, InputDataValidationException;

    public List<Customer> retrieveAllCustomers();
    
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateCustomerPassword(Long customerId, String password) throws CustomerNotFoundException, UpdateCustomerException, InputDataValidationException;

    public void addFavouriteService(Long customerId, Long serviceId) throws CustomerNotFoundException, ServiceNotFoundException;

    public void addFavouriteProduct(Long customerId, Long favouriteProductId) throws CustomerNotFoundException, ProductNotFoundException;

    public void removeFavouriteProduct(Long customerId, Long favouriteProductId) throws CustomerNotFoundException, ProductNotFoundException;

    public void removeFavouriteService(Long customerId, Long serviceId) throws CustomerNotFoundException, ServiceNotFoundException;
    
}
