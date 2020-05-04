import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { SalesForUsService } from '../sales-for-us.service';
import { ServiceProvider } from '../service-provider';
import { SalesForUs } from '../sales-for-us';
import { FilterUtils } from 'primeng/utils';



@Component({
  selector: 'app-view-all-service-provider-salesfor-us',
  templateUrl: './view-all-service-provider-salesfor-us.component.html',
  styleUrls: ['./view-all-service-provider-salesfor-us.component.css']
})

export class ViewAllServiceProviderSalesforUsComponent implements OnInit
{
	salesForUs: SalesForUs[];
	cols: any[];
	display: boolean = false;
	serviceProviderToView: ServiceProvider;
	
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private salesForUsService: SalesForUsService,
				)
	{	
		
	}



	ngOnInit() 
	{		
		this.salesForUsService.getServiceProviderSalesRecords().subscribe(
			response => {
				this.salesForUs = response.salesForUs;

			},
			error => {
				console.log('********** ViewAllServiceProviderSalesRecordComponent.ts: ' + error);
			}
		);
		
		this.cols = [
            { field: 'salesForUsId', header: 'Sales Record ID' },
			{ field: 'serviceProvider.serviceProviderId', header: 'Service Provider ID' },
            { field: 'serviceProvider.name', header: 'Service Provider Name' },
            { field: 'dateOfPayment', header: 'Date Of Payment' },
            { field: 'amount', header: 'Amount Earned' },
			{ field: '', header: 'View Service Provider Details' }
        ];
		
	}
	
	showDialog(serviceProviderToView: ServiceProvider)
	{
        this.display = true;
		this.serviceProviderToView = serviceProviderToView;
    }
	
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}
	
}