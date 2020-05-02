import { Customer } from './customer';
import { Service } from './service';
import { Review } from './review';
import * as moment from 'moment';

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
	
	
	constructor(bookingId?: number, dateOfBooking?: Date, status?: string, remarks?: string, dateOfAppointment?: Date, startTime?: Date, endTime?: Date)
	{
		this.bookingId = bookingId;
		this.dateOfBooking = dateOfBooking;
        this.status = status;
        this.remarks = remarks;
        this.dateOfAppointment = dateOfAppointment;
        this.startTime = startTime;
        this.endTime = endTime;
	}
}
