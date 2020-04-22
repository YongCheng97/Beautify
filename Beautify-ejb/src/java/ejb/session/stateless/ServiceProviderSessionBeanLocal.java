/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ServiceProvider;
import java.util.List;
import util.exception.DeleteServiceProviderException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ServiceProviderExistException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateServiceProviderException;

public interface ServiceProviderSessionBeanLocal {

    public Long createNewServiceProvider(ServiceProvider newServiceProvider) throws ServiceProviderExistException, UnknownPersistenceException, InputDataValidationException;

    public List<ServiceProvider> retrieveAllServiceProviders();

    public ServiceProvider retrieveServiceProviderById(Long serviceProviderId) throws ServiceProviderNotFoundException;

    public ServiceProvider retrieveServiceProviderByName(String name) throws ServiceProviderNotFoundException;

    public ServiceProvider serviceProviderLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateStaff(ServiceProvider serviceProvider) throws ServiceProviderNotFoundException, UpdateServiceProviderException, InputDataValidationException;

    public void deleteServiceProvider(Long serviceProviderId) throws ServiceProviderNotFoundException, DeleteServiceProviderException;

    public List<ServiceProvider> searchServiceProviderByName(String searchString);

    public List<ServiceProvider> filterServiceProviderByTags(List<Long> tagIds, String condition); 

    public ServiceProvider retrieveServiceProviderByEmail(String email) throws ServiceProviderNotFoundException;
    
}
