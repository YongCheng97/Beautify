import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { SalesRecordService } from '../sales-record.service';
import { Booking } from '../booking';
import { SalesRecord } from '../sales-record';


@Component({
  selector: 'app-view-all-booking-sales-record',
  templateUrl: './view-all-booking-sales-record.component.html',
  styleUrls: ['./view-all-booking-sales-record.component.css']
})

export class ViewAllBookingSalesRecordComponent implements OnInit
{
	salesRecords: SalesRecord[];
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