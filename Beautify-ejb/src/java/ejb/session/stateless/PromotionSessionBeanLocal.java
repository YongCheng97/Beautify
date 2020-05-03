/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import entity.Promotion;
import entity.Service;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeletePromotionException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNameExistException;
import util.exception.PromotionNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePromotionException;

/**
 *
 * @author fooyo
 */
@Local
public interface PromotionSessionBeanLocal {

    public Promotion createNewPromotion(Promotion newPromotion, Long serviceProviderId) throws UnknownPersistenceException, InputDataValidationException, PromotionNameExistException;

    public List<Promotion> retrieveAllPromotions();

    public Promotion retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException;

    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromotionNotFoundException;

    public Promotion retrievePromotionByName(String name) throws PromotionNotFoundException;

    public void updatePromotion(Promotion promotion) throws PromotionNotFoundException, UpdatePromotionException, InputDataValidationException;

    public void deletePromotion(Long promotionId) throws PromotionNotFoundException, DeletePromotionException;

    public Boolean checkPromoCode(String promoCode);
    
    public List<Promotion> retrievePromotionsByServiceProviderId(Long serviceProviderId);

}
