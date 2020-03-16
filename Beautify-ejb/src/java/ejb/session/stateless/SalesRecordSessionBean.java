package ejb.session.stateless;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(SalesRecordSessionBeanLocal.class)

public class SalesRecordSessionBean implements SalesRecordSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    public SalesRecordSessionBean() {
        
    }
}
