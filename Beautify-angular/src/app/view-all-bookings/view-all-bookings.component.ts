import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { BookingService } from '../booking.service';
import { Booking } from '../booking';

@Component({
  selector: 'app-view-all-bookings',
  templateUrl: './view-all-bookings.component.html',
  styleUrls: ['./view-all-bookings.component.css']
})

export class ViewAllBookingsComponent implements OnInit
{
	bookings: Booking[];
	display: boolean = false;
	displayStatus: boolean = false;
	bookingToView: Booking;
	statuses: string[];
	selectedStatus: string;
	submitted: boolean;
	
	resultSuccess: boolean;
	resultError: boolean;
	message: string;
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private bookingService: BookingService
				)
	{	
		this.submitted = false;
	
		this.resultSuccess = false;
		this.resultError = false;
	}



	ngOnInit() 
	{		
		this.bookingService.getBookings().subscribe(
			response => {
				this.bookings = response.bookings;
				/* for (let booking of this.bookings) {
					booking.dateOfAppointment = booking.dateOfAppointment.substring(0,10);
					booking.dateOfBooking = booking.dateOfBooking.substring(0,10);
					
					var localTime = moment.utc(booking.startTime).local().format();
					booking.startTime = localTime;
				} */
			},
			error => {
				console.log('********** ViewAllBookingsComponent.ts: ' + error);
			}
		);
		
		this.statuses = ['Approved', 'Completed', 'Cancelled']; 
	}
	
	showDialog(bookingToView: Booking)
	{
        this.display = true;
		this.bookingToView = bookingToView;
    }
	
	showStatusDialog(bookingToView: Booking)
	{
        this.displayStatus = true;
		this.bookingToView = bookingToView;
    }
	
	update(updateBookingStatusForm: NgForm)
	{
		
		this.submitted = true;
		
		if (updateBookingStatusForm.valid) 
		{
			this.bookingToView.status = this.selectedStatus;
			
			this.bookingService.updateBooking(this.bookingToView).subscribe(
				response => {					
					this.resultSuccess = true;
					this.resultError = false;
					this.message = "Booking status updated successfully";
				},
				error => {
					this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while updating the status: " + error;
					
					console.log('********** ViewAllBookingsComponent.ts: ' + error);
				}
			);
		}
	}
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}
	
}