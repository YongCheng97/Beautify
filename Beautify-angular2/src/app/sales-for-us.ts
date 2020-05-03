import { PurchasedLineItem } from './purchased-line-item';
import { Booking } from './booking';

export class SalesForUs
{
	salesForUsId: number;
	amount: number;
	dateOfPayment: Date;
	purchasedLineItem: PurchasedLineItem;
	booking: Booking;
	
	
	constructor(salesForUsId?: number, amount?: number, dateOfPayment?: Date)
	{
		this.salesForUsId = salesForUsId;
		this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        
	}
}
