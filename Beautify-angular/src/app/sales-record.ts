import { PurchasedLineItem } from './purchased-line-item';
import { Booking } from './booking';

export class SalesRecord
{
	salesRecordId: number;
	amount: number;
	dateOfPayment: Date;
	purchasedLineItem: PurchasedLineItem;
	booking: Booking;
	
	
	constructor(salesRecordId?: number, amount?: number, dateOfPayment?: Date)
	{
		this.salesRecordId = salesRecordId;
		this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        
	}
}
