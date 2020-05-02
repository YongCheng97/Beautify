import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { SalesRecordService } from '../sales-record.service';
import { Booking } from '../booking';
import { SalesRecord } from '../sales-record';
import { FilterUtils } from 'primeng/utils';



@Component({
  selector: 'app-view-all-booking-sales-record',
  templateUrl: './view-all-booking-sales-record.component.html',
  styleUrls: ['./view-all-booking-sales-record.component.css']
})

export class ViewAllBookingSalesRecordComponent implements OnInit
{
	salesRecords: SalesRecord[];
	cols: any[];
	display: boolean = false;
	bookingToView: Booking;
	
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private salesRecordService: SalesRecordService,
				)
	{	
		
	}



	ngOnInit() 
	{		
		this.salesRecordService.getBookingSalesRecords().subscribe(
			response => {
				this.salesRecords = response.salesRecords;

			},
			error => {
				console.log('********** ViewAllBookingSalesRecordComponent.ts: ' + error);
			}
		);
		
		this.cols = [
            { field: 'salesRecordId', header: 'Sales Record ID' },
            { field: 'booking.bookingId', header: 'Booking ID' },
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