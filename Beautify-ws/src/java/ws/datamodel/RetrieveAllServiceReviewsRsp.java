/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Review;
import java.util.List;

/**
 *
 * @author jilon
 */
public class RetrieveAllServiceReviewsRsp {

    private List<Review> reviews;

    public RetrieveAllServiceReviewsRsp() {
    }

    public RetrieveAllServiceReviewsRsp(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    

}
