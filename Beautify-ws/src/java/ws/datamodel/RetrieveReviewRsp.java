package ws.datamodel;

import entity.Review;


public class RetrieveReviewRsp {
    
    private Review review; 

    public RetrieveReviewRsp() {
    }

    public RetrieveReviewRsp(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
    
    
    
}
