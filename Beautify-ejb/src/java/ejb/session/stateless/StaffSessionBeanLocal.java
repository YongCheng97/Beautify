package ejb.session.stateless;

import entity.Staff;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStaffException;

public interface StaffSessionBeanLocal {

    public Long createNewStaff(Staff newStaff) throws StaffUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Staff> retrieveAllStaffs();

    public Staff retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException;

    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;

    public Staff staffLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateStaff(Staff staffEntity) throws StaffNotFoundException, UpdateStaffException, InputDataValidationException;

    public void deleteStaff(Long staffId) throws StaffNotFoundException;
    
}
