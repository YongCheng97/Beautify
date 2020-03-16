package ejb.session.stateless;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ReviewSessionBeanLocal.class)
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    public ReviewSessionBean() {
        
    }
}
