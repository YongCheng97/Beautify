export class Review 
{
    reviewId: number; 
    rating: number; 
    description: string; 

    constructor(reviewId?: number, rating?: number, description?: string) 
    {
        this.reviewId = reviewId; 
        this.rating = rating; 
        this.description = description; 
    }

}
