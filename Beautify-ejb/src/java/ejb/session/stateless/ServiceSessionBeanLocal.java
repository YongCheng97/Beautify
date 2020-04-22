package ejb.session.stateless;

import entity.Service;
import java.math.BigDecimal;
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

    public Service createNewService(Service newService, Long serviceProviderId, Long categoryId, List<Long> tagIds) throws ServiceExistException, UnknownPersistenceException, InputDataValidationException, CreateNewServiceException;

    public List<Service> retrieveAllServices();

    public List<Service> filterServicesByName(String searchString, Long categoryId);

    public List<Service> filterServicesByCategory(Long categoryId) throws CategoryNotFoundException;

    public List<Service> filterServicesByTags(List<Long> tagIds, String condition, Long categoryId);

    public List<Service> retrieveAllServicesFromCategory(Long categoryId);

    public List<Service> filterServicesByMinimumPrice(BigDecimal minPrice, Long categoryId);

    public List<Service> filterServicesByMaximumPrice(BigDecimal maxPrice, Long categoryId);
    
}
