import { Review } from './review';
import { Product } from './product';
import { Purchased } from './purchased';

export class PurchasedLineItem {
	
	purchasedLineItemId: number;
	quantity: number;
	status: string;
	price: number;
	product: Product;
	review: Review;
	purchased: Purchased
	
	
	constructor(purchasedLineItemId?: number, quantity?: number, status?: string, price?: number)
	{
		this.purchasedLineItemId = purchasedLineItemId;
		this.quantity = quantity;
        this.status = status;
        this.price = price;
	}
}
