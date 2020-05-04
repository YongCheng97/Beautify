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

    public Promotion createNewServicePromotion(Promotion newPromotion, Long serviceProviderId, Long serviceId) throws UnknownPersistenceException, InputDataValidationException, PromotionNameExistException;

    public Promotion createNewProductPromotion(Promotion newPromotion, Long serviceProviderId, Long productId) throws UnknownPersistenceException, InputDataValidationException, PromotionNameExistException;

    public List<Promotion> retrieveAllPromotions();

    public Promotion retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException;

    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromotionNotFoundException;

    public Promotion retrievePromotionByName(String name) throws PromotionNotFoundException;

    public void updateProductPromotion(Promotion promotion, Long productId) throws PromotionNotFoundException, UpdatePromotionException, InputDataValidationException;

    public void updateServicePromotion(Promotion promotion, Long serviceId) throws PromotionNotFoundException, UpdatePromotionException, InputDataValidationException;

    public void deletePromotion(Long promotionId) throws PromotionNotFoundException, DeletePromotionException;

    public Boolean checkPromoCode(String promoCode);

    public List<Promotion> retrievePromotionsByServiceProviderId(Long serviceProviderId);

    public List<Promotion> retrieveAllServicePromotionsByServiceProviderId(Long serviceProviderId);

    public List<Promotion> retrieveAllProductPromotionsByServiceProviderId(Long serviceProviderId);

}
