import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { PurchasedLineItemService } from '../purchased-line-item.service';
import { PurchasedLineItem } from '../purchased-line-item';

@Component({
  selector: 'app-view-all-purchased-line-items',
  templateUrl: './view-all-purchased-line-items.component.html',
  styleUrls: ['./view-all-purchased-line-items.component.css']
})

export class ViewAllPurchasedLineItemsComponent implements OnInit {

	purchasedLineItems: PurchasedLineItem[];
	display: boolean = false;
	displayStatus: boolean = false;
	purchasedLineItemToView: PurchasedLineItem;
	statuses: string[];
	selectedStatus: string;
	submitted: boolean;
	
	resultSuccess: boolean;
	resultError: boolean;
	message: string;
	
  constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		public sessionService: SessionService,
		private purchasedLineItemService: PurchasedLineItemService
  ) { 
  
  }

	ngOnInit() 
	{		
		this.purchasedLineItemService.getPurchasedLineItems().subscribe(
			response => {
				this.purchasedLineItems = response.purchasedLineItems;
			},
			error => {
				console.log('********** ViewAllPurchasedLineItemsComponent.ts: ' + error);
			}
		);
		
		this.statuses = ['Order Confirmed', 'Shipped', 'Cancelled']; 
	}
	
	showDialog(purchasedLineItemToView: PurchasedLineItem)
	{
        this.display = true;
		this.purchasedLineItemToView = purchasedLineItemToView;
    }
	
	showStatusDialog(purchasedLineItemToView: PurchasedLineItem)
	{
        this.displayStatus = true;
		this.purchasedLineItemToView = purchasedLineItemToView;
    }
	
	update(updatePurchasedLineItemStatusForm: NgForm)
	{
		
		this.submitted = true;
		
		if (updatePurchasedLineItemStatusForm.valid) 
		{
			this.purchasedLineItemToView.status = this.selectedStatus;
			
			this.purchasedLineItemService.updatePurchasedLineItem(this.purchasedLineItemToView).subscribe(
				response => {					
					this.resultSuccess = true;
					this.resultError = false;
					this.message = "Order Item status updated successfully";
				},
				error => {
					this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while updating the status: " + error;
					
					console.log('********** ViewAllPurchasedLineItemsComponent.ts: ' + error);
				}
			);
		}
	}
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}

}
