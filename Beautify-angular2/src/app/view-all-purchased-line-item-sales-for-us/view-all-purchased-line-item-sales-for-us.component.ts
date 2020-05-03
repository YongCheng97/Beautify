import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { SalesForUsService } from '../sales-for-us.service';
import { PurchasedLineItem } from '../purchased-line-item';
import { SalesForUs } from '../sales-for-us';


@Component({
  selector: 'app-view-all-purchased-line-item-sales-for-us',
  templateUrl: './view-all-purchased-line-item-sales-for-us.component.html',
  styleUrls: ['./view-all-purchased-line-item-sales-for-us.component.css']
})

export class ViewAllPurchasedLineItemSalesForUsComponent implements OnInit
{
	salesForUs: SalesForUs[];
	cols: any[];
	display: boolean = false;
	purchasedLineItemToView: PurchasedLineItem;
	
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private salesForUsService: SalesForUsService,
				)
	{	
		
	}



	ngOnInit() 
	{		
		this.salesForUsService.getPurchasedLineItemSalesRecords().subscribe(
			response => {
				this.salesForUs = response.salesForUs;

			},
			error => {
				console.log('********** ViewAllPurchasedLineItemSalesForUsComponent.ts: ' + error);
			}
		);
		
		this.cols = [
            { field: 'salesForUsId', header: 'Sales Record ID' },
            { field: 'purchasedLineItem.purchasedLineItemId', header: 'Order Item ID' },
			{ field: 'purchasedLineItem.product.serviceProvider.name', header: 'Service Provider' },
            { field: 'dateOfPayment', header: 'Date Of Payment' },
            { field: 'amount', header: 'Amount Earned' },
			{ field: '', header: 'View Order Item Details' }
        ];
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