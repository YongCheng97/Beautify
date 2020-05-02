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
  serviceProviderToUpdate: ServiceProvider; 
  approvalStatus = "";

  displayName: boolean = false;
  displayEmail: boolean = false;
  displayAddress: boolean = false;
  displayHours: boolean = false;
  days: string[]

  nameSubmitted: boolean;
  newName: string
  emailSubmitted: boolean = false;
  newEmail: string
  addressSubmitted: boolean = false;
  newAddress: string

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(public sessionService: SessionService,
    public serviceProviderService: ServiceProviderService) {
      this.nameSubmitted = false; 
      this.resultSuccess = false; 
      this.resultError = false; 
  }

  ngOnInit() {
    this.serviceProvider = this.sessionService.getCurrentServiceProvider();

    if (this.serviceProvider.isApproved) {
      this.approvalStatus = "Approved";
    } else {
      this.approvalStatus = "Pending Approval";
    }

    this.days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Saturday', 'Sunday'];

  }

  showNameDialog(serviceProvider: ServiceProvider) {
    this.displayName = true;
    this.serviceProviderToUpdate = serviceProvider;

    console.log(this.serviceProviderToUpdate.name); 
  }

  closeNameDialog() {
    this.displayName = false; 
  }

  showEmailDialog(serviceProvider: ServiceProvider) {
    this.displayEmail = true;
    this.serviceProviderToUpdate = serviceProvider;
  }

  showAddressDialog(serviceProvider: ServiceProvider) {
    this.displayAddress = true;
    this.serviceProvider = serviceProvider;
  }

  showHoursDialog(serviceProvider: ServiceProvider) {
    this.displayHours = true;
    this.serviceProvider = serviceProvider;
  }

  updateName(updateNameForm: NgForm) {

    console.log("update name"); 

    this.nameSubmitted = true;

    if (updateNameForm.valid) {

      this.serviceProviderService.updateServiceProvider(this.serviceProviderToUpdate).subscribe(
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


      console.log("******** " + this.serviceProviderToUpdate.password);
      this.serviceProviderService.updateServiceProvider(this.serviceProviderToUpdate).subscribe(
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
