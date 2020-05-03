import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { SalesForUsService } from '../sales-for-us.service';
import { Booking } from '../booking';
import { SalesForUs } from '../sales-for-us';
import { FilterUtils } from 'primeng/utils';



@Component({
  selector: 'app-view-all-booking-sales-for-us',
  templateUrl: './view-all-booking-sales-for-us.component.html',
  styleUrls: ['./view-all-booking-sales-for-us.component.css']
})

export class ViewAllBookingSalesForUsComponent implements OnInit
{
	salesForUs: SalesForUs[];
	cols: any[];
	display: boolean = false;
	bookingToView: Booking;
	
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private salesForUsService: SalesForUsService,
				)
	{	
		
	}



	ngOnInit() 
	{		
		this.salesForUsService.getBookingSalesRecords().subscribe(
			response => {
				this.salesForUs = response.salesForUs;

			},
			error => {
				console.log('********** ViewAllBookingSalesRecordComponent.ts: ' + error);
			}
		);
		
		this.cols = [
            { field: 'salesForUsId', header: 'Sales Record ID' },
			{ field: 'booking.bookingId', header: 'Booking ID' },
			{ field: 'booking.service.serviceProvider.name', header: 'Service Provider' },
            { field: 'dateOfPayment', header: 'Date Of Payment' },
            { field: 'amount', header: 'Amount Earned' },
			{ field: '', header: 'View Booking Details' }
        ];
		
	}
	
	showDialog(bookingToView: Booking)
	{
        this.display = true;
		this.bookingToView = bookingToView;
    }
	
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}
	
}