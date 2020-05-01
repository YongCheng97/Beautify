import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

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

  nameSubmitted: boolean = false;
  newName: string
  emailSubmitted: boolean = false;
  newEmail: string
  addressSubmitted: boolean = false;
  newAddress: string

  resultSuccess: boolean = false;
  resultError: boolean = false;
  message: string;

  constructor(public sessionService: SessionService,
    public serviceProviderService: ServiceProviderService) {

  }

  ngOnInit() {
    this.serviceProvider = this.sessionService.getCurrentServiceProvider();

    if (this.serviceProvider.isApproved) {
      this.approvalStatus = "Approved";
    } else {
      this.approvalStatus = "Pending Approval";
    }

  }

  showNameDialog(serviceProvider: ServiceProvider) {
    this.displayName = true;
    this.serviceProvider = serviceProvider;
  }

  showEmailDialog(serviceProvider: ServiceProvider) {
    this.displayEmail = true;
    this.serviceProvider = serviceProvider;
  }

  showAddressDialog(serviceProvider: ServiceProvider) {
    this.displayAddress = true;
    this.serviceProvider = serviceProvider;
  }

  updateName(updateNameForm: NgForm) {
    this.nameSubmitted = true;

    if (updateNameForm.valid) {
      this.serviceProvider.name = this.newName;

      this.serviceProviderService.updateServiceProvider(this.serviceProvider).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Name updated successfully";
        },
        error => {
          this.resultError = true; 
          this.resultSuccess = false; 
          this.message = "An error has occured while updating the name: " + error; 

					console.log('********** MainPageComponent.ts: ' + error);
        }
      )
    }
  }

  updateEmail(updateEmailForm: NgForm) {
    this.nameSubmitted = true;

    if (updateEmailForm.valid) {
      this.serviceProvider.email = this.newEmail;

      this.serviceProviderService.updateServiceProvider(this.serviceProvider).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Name updated successfully";
        },
        error => {
          this.resultError = true; 
          this.resultSuccess = false; 
          this.message = "An error has occured while updating the email: " + error; 

					console.log('********** MainPageComponent.ts: ' + error);
        }
      )
    }
  }

  updateAddress(updateAddressForm: NgForm) {
    this.addressSubmitted = true;

    if (updateAddressForm.valid) {
      this.serviceProvider.address = this.newAddress;

      this.serviceProviderService.updateServiceProvider(this.serviceProvider).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Name updated successfully";
        },
        error => {
          this.resultError = true; 
          this.resultSuccess = false; 
          this.message = "An error has occured while updating the address: " + error; 

					console.log('********** MainPageComponent.ts: ' + error);
        }
      )
    }
  }

}
