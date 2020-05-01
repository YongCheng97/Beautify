import { Customer } from './customer';
import { CreditCard } from './credit-card';
import { PurchasedLineItem } from './purchased-line-item';

export class Purchased {
	
	purchasedId: number;
	dateOfPurchase: Date;
	totalPrice: number;
	address: string;
	purchasedLineItems: PurchasedLineItem[]
	customer: Customer
	creditCard: CreditCard
	
	
	constructor(purchasedId?: number, dateOfPurchase?: Date, totalPrice?: number, address?: string)
	{
		this.purchasedId = purchasedId;
		this.dateOfPurchase = dateOfPurchase;
        this.totalPrice = totalPrice;
        this.address = address;
	}
}
