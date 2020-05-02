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
  displayCC: boolean = false; 

  nameSubmitted: boolean;
  newName: string
  emailSubmitted: boolean;
  newEmail: string
  addressSubmitted: boolean;
  newAddress: string

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(public sessionService: SessionService,
    public serviceProviderService: ServiceProviderService) {
    this.nameSubmitted = false;
    this.emailSubmitted = false;
    this.addressSubmitted = false;
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
  }

  closeNameDialog() {
    this.displayName = false;
  }

  showEmailDialog(serviceProvider: ServiceProvider) {
    this.displayEmail = true;
    this.serviceProviderToUpdate = serviceProvider;
  }

  closeEmailDialog() {
    this.displayEmail = false;
  }

  showAddressDialog(serviceProvider: ServiceProvider) {
    this.displayAddress = true;
    this.serviceProviderToUpdate = serviceProvider;
  }

  closeAddressDialog() {
    this.displayAddress = false;
  }

  showHoursDialog(serviceProvider: ServiceProvider) {
    this.displayHours = true;
    this.serviceProvider = serviceProvider;
  }

  showCCDialog(serviceProvider: ServiceProvider) {
    this.displayCC = true;
    this.serviceProvider = serviceProvider;
  }

  updateName(updateNameForm: NgForm) {

    this.nameSubmitted = true;

    if (updateNameForm.valid) {

      this.serviceProviderToUpdate.name = this.newName;

      this.serviceProviderService.updateServiceProvider(this.serviceProviderToUpdate).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Name updated successfully";
          this.sessionService.setCurrentServiceProvider(this.serviceProviderToUpdate);
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
    this.emailSubmitted = true;

    if (updateEmailForm.valid) {

      this.serviceProviderToUpdate.email = this.newEmail;

      this.serviceProviderService.updateServiceProvider(this.serviceProviderToUpdate).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Name updated successfully";
          this.sessionService.setCurrentServiceProvider(this.serviceProviderToUpdate);
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

      this.serviceProviderToUpdate.address = this.newAddress;

      this.serviceProviderService.updateServiceProvider(this.serviceProviderToUpdate).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Name updated successfully";
          this.sessionService.setCurrentServiceProvider(this.serviceProviderToUpdate);
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
