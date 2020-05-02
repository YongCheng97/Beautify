import { Customer } from './customer';
import { Service } from './service';
import { Review } from './review';

export class Booking
{
	bookingId: number;
	dateOfBooking: Date;
	status: string;
	remarks: string;
	dateOfAppointment: Date;
	startTime: Date;
	endTime: Date;
	customer: Customer;
	service: Service;
	review: Review;
	price: number;
	
	
	constructor(bookingId?: number, dateOfBooking?: Date, status?: string, remarks?: string, dateOfAppointment?: Date, startTime?: Date, endTime?: Date, price?: number)
	{
		this.bookingId = bookingId;
		this.dateOfBooking = dateOfBooking;
        this.status = status;
        this.remarks = remarks;
        this.dateOfAppointment = dateOfAppointment;
        this.startTime = startTime;
        this.endTime = endTime;
		this.price = price;
	}
}
