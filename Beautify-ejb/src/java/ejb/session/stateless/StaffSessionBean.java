package ejb.session.stateless;

import entity.Staff;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStaffException;
import util.security.CryptographicHelper;


@Stateless
@Local(StaffSessionBeanLocal.class)
public class StaffSessionBean implements StaffSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StaffSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewStaff(Staff newStaff) throws StaffUsernameExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<Staff>>constraintViolations = validator.validate(newStaff);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newStaff);
                em.flush();

                return newStaff.getStaffId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new StaffUsernameExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public List<Staff> retrieveAllStaffs()
    {
        Query query = em.createQuery("SELECT s FROM Staff s");
        
        return query.getResultList();
    }
    
    
    
    @Override
    public Staff retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException
    {
        Staff staff = em.find(Staff.class, staffId);
        
        if(staff != null)
        {
            return staff;
        }
        else
        {
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }
    
    @Override
    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (Staff)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }
    
    
    
    // Updated in v4.5 to include password hashing
    
    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Staff staffEntity = retrieveStaffByUsername(username);            
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + staffEntity.getSalt()));
            
            if(staffEntity.getPassword().equals(passwordHash))
            {
                                
                return staffEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public void updateStaff(Staff staffEntity) throws StaffNotFoundException, UpdateStaffException, InputDataValidationException
    {
        if(staffEntity != null && staffEntity.getStaffId() != null)
        {
            Set<ConstraintViolation<Staff>>constraintViolations = validator.validate(staffEntity);
        
            if(constraintViolations.isEmpty())
            {
                Staff staffEntityToUpdate = retrieveStaffByStaffId(staffEntity.getStaffId());

                if(staffEntityToUpdate.getUsername().equals(staffEntity.getUsername()))
                {
                    staffEntityToUpdate.setEmail(staffEntity.getEmail());
                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
                }
                else
                {
                    throw new UpdateStaffException("Username of staff record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new StaffNotFoundException("Staff ID not provided for staff to be updated");
        }
    }
    
    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException
    {
        Staff staffEntityToRemove = retrieveStaffByStaffId(staffId);

        em.remove(staffEntityToRemove);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Staff>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
