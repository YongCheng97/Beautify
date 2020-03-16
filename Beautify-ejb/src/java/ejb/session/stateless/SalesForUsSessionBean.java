package ejb.session.stateless;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(SalesForUsSessionBeanLocal.class)
public class SalesForUsSessionBean implements SalesForUsSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    public SalesForUsSessionBean() {
        
    }
}
