package ejb.session.stateless;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(PromotionSessionBeanLocal.class)

public class PromotionSessionBean implements PromotionSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    public PromotionSessionBean() {
    }

    
}
