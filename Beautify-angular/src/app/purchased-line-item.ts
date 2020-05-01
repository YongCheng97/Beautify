import { Review } from './review';
import { Product } from './product';

export class PurchasedLineItem {
	
	purchasedLineItemId: number;
	quantity: number;
	status: string;
	price: number;
	product: Product;
	review: Review;
	
	
	constructor(purchasedLineItemId?: number, quantity?: number, status?: string, price?: number)
	{
		this.purchasedLineItemId = purchasedLineItemId;
		this.quantity = quantity;
        this.status = status;
        this.price = price;
	}
}
