import { Customer } from './customer'; 
import { Booking } from './booking'; 
import { PurchasedLineItem } from './purchased-line-item'; 

export class Review 
{
    reviewId: number; 
    rating: number; 
    description: string; 
    customer: Customer; 
    booking: Booking; 
    purchasedLineItem: PurchasedLineItem; 


    constructor(reviewId?: number, rating?: number, description?: string) 
    {
        this.reviewId = reviewId; 
        this.rating = rating; 
        this.description = description; 
    }

}
