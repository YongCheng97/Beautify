import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { SalesRecordService } from '../sales-record.service';
import { PurchasedLineItem } from '../purchased-line-item';
import { SalesRecord } from '../sales-record';


@Component({
  selector: 'app-view-all-purchased-line-item-sales-record',
  templateUrl: './view-all-purchased-line-item-sales-record.component.html',
  styleUrls: ['./view-all-purchased-line-item-sales-record.component.css']
})

export class ViewAllPurchasedLineItemSalesRecordComponent implements OnInit
{
	salesRecords: SalesRecord[];
	display: boolean = false;
	purchasedLineItemToView: PurchasedLineItem;
	
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private salesRecordService: SalesRecordService,
				)
	{	
		
	}



	ngOnInit() 
	{		
		this.salesRecordService.getPurchasedLineItemSalesRecords().subscribe(
			response => {
				this.salesRecords = response.salesRecords;

			},
			error => {
				console.log('********** ViewAllPurchasedLineItemSalesRecordComponent.ts: ' + error);
			}
		);
		
	}
	
	showDialog(purchasedLineItemToView: PurchasedLineItem)
	{
        this.display = true;
		this.purchasedLineItemToView = purchasedLineItemToView;
    }
	
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}
	
}