import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { BookingService } from '../booking.service';
import { Booking } from '../booking';
import { FilterUtils } from 'primeng/utils';


@Component({
  selector: 'app-view-all-bookings',
  templateUrl: './view-all-bookings.component.html',
  styleUrls: ['./view-all-bookings.component.css']
})

export class ViewAllBookingsComponent implements OnInit
{
	bookings: Booking[];
	cols: any[];
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
				
			},
			error => {
				console.log('********** ViewAllBookingsComponent.ts: ' + error);
			}
		);
		
		this.statuses = ['Approved', 'Completed', 'Cancelled']; 
		
		this.cols = [
            { field: 'bookingId', header: 'Booking ID' },
            { field: 'service.serviceName', header: 'Service' },
            { field: 'customer.firstName', header: 'Customer Name' },
            { field: 'dateOfAppointment', header: 'Date Of Appointment' },
			{ field: 'startTime', header: 'Start Time' },
            { field: 'endTime', header: 'End Time' },
            { field: 'status', header: 'Status' },
            { field: '', header: 'View Details' },
			{ field: '', header: 'Update Status' }
        ];

		
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
		
		this.displayStatus = false;
	}
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}
	
}