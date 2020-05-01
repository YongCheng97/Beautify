import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ServiceProviderService } from '../service-provider.service';
import { ServiceProvider } from '../service-provider';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

export class MainPageComponent implements OnInit {

  serviceProvider: ServiceProvider;
  approvalStatus = ""; 
  displayName: boolean = false; 
  displayEmail: boolean = false; 
  displayAddress: boolean = false; 


  constructor(public sessionService: SessionService,
    public serviceProviderService: ServiceProviderService) 
  { 

  }

  ngOnInit() 
  {
    this.serviceProvider = this.sessionService.getCurrentServiceProvider();  

    if (this.serviceProvider.isApproved) {
      this.approvalStatus = "Approved"; 
    } else {
      this.approvalStatus = "Pending Approval"; 
    }

  }

  showNameDialog(serviceProvider: ServiceProvider) 
  {
    this.displayName = true; 
    this.serviceProvider = serviceProvider; 
  }

  showEmailDialog(serviceProvider: ServiceProvider) 
  {
    this.displayEmail = true; 
    this.serviceProvider = serviceProvider; 
  }

  showAddressDialog(serviceProvider: ServiceProvider) 
  {
    this.displayAddress = true; 
    this.serviceProvider = serviceProvider; 
  }

}
