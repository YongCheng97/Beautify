/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Review;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jilon
 */
@Named(value = "reviewsManagedBean")
@ViewScoped
public class ReviewsManagedBean {

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;
    
    private List<Review> reviews; 

    public ReviewsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        setReviews(reviewSessionBeanLocal.retrieveAllReviews()); 
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    
}
