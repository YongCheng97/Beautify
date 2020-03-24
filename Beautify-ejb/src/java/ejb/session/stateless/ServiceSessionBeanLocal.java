package ejb.session.stateless;

import entity.Service;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewServiceException;
import util.exception.InputDataValidationException;
import util.exception.ServiceExistException;
import util.exception.ServiceNotFoundException;
import util.exception.UnknownPersistenceException;


public interface ServiceSessionBeanLocal {

    public Service retrieveServiceByServiceId(Long serviceId) throws ServiceNotFoundException;

    public Service createNewService(Service newService, Long serviceProviderId, Long categoryId) throws ServiceExistException, UnknownPersistenceException, InputDataValidationException, CreateNewServiceException;

    public List<Service> retrieveAllServices();

    public List<Service> searchServicesByName(String searchString);

    public List<Service> filterServicesByCategory(Long categoryId) throws CategoryNotFoundException;

    public List<Service> filterServicesByTags(List<Long> tagIds, String condition);
    
}
