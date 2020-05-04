import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { ServiceProviderService } from '../service-provider.service';
import { ServiceProvider } from '../service-provider';
import { FilterUtils } from 'primeng/utils';


@Component({
  selector: 'app-view-all-service-providers',
  templateUrl: './view-all-service-providers.component.html',
  styleUrls: ['./view-all-service-providers.component.css']
})

export class ViewAllServiceProvidersComponent implements OnInit
{
	serviceProviders: ServiceProvider[];
	cols: any[];
	display: boolean = false;
	serviceProviderToView: ServiceProvider;
	submitted: boolean;
	
	resultSuccess: boolean;
	resultError: boolean;
	message: string;
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private serviceProviderService: ServiceProviderService
				)
	{	
		this.submitted = false;
	
		this.resultSuccess = false;
		this.resultError = false;
	}



	ngOnInit() 
	{		
		this.serviceProviderService.getServiceProviders().subscribe(
			response => {
				this.serviceProviders = response.serviceProviders;
				
			},
			error => {
				console.log('********** ViewAllServiceProvidersComponent.ts: ' + error);
			}
		);
		
	
		this.cols = [
            { field: 'serviceProviderId', header: 'Service Provider ID' },
            { field: 'name', header: 'Service Provider Name' },
            { field: 'isApproved', header: 'Approval Status' },
            { field: '', header: 'View Details' }
        ];
		
	}
	
	showDialog(serviceProviderToView: ServiceProvider)
	{
        this.display = true;
		this.serviceProviderToView = serviceProviderToView;
    }
	
	update(updateStatusForm: NgForm)
	{
		if (updateStatusForm.valid) 
		{
		
			this.serviceProviderToView.isApproved = true;
		
			this.serviceProviderService.updateServiceProvider(this.serviceProviderToView).subscribe(
				response => {					
					this.resultSuccess = true;
					this.resultError = false;
					this.message = "Service Provider status updated successfully";
				},
				error => {
					this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while updating the status: " + error;
					
					console.log('********** ViewAllServiceProviderComponent.ts: ' + error);
				}
			);
		}
		
		this.display = false;
	}
	
	parseDate(d: Date)
	{		
		if (d != null) {
			return d.toString().replace('[UTC]', '');
		}
	}
	
}