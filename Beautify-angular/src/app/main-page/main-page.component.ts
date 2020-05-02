import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { ServiceProviderService } from '../service-provider.service';
import { ServiceProvider } from '../service-provider';
import { CreditCard } from '../credit-card'; 
import { CreditCardService } from '../credit-card.service'; 

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

export class MainPageComponent implements OnInit {

  serviceProvider: ServiceProvider;
  serviceProviderToUpdate: ServiceProvider;
  approvalStatus = "";

  creditCards: CreditCard[]; 
  creditCardToDelete: CreditCard; 
  error: boolean; 
  errorMessage: string; 
  creditCardId: number; 
  newCreditCard: CreditCard; 
  ccSubmitted: boolean; 

  displayAddCC: boolean = false; 
  types: string[]; 
  ccType: string; 
  ccName: string; 
  ccNum: string; 
  ccDate: string; 

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
    private serviceProviderService: ServiceProviderService, 
    private creditCardService: CreditCardService,
    private router: Router, 
    private activatedRouter: ActivatedRoute
    )
  {
    this.nameSubmitted = false;
    this.emailSubmitted = false;
    this.addressSubmitted = false;
    this.resultSuccess = false;
    this.resultError = false;

    this.newCreditCard = new CreditCard(); 

    this.error = false; 
  }

  ngOnInit() {
    this.serviceProvider = this.sessionService.getCurrentServiceProvider();

    if (this.serviceProvider.isApproved) {
      this.approvalStatus = "Approved";
    } else {
      this.approvalStatus = "Pending Approval";
    }

    this.creditCardService.getCreditCards().subscribe(
      response => {
        this.creditCards = response.creditCards; 
      }, 
      error => {
        console.log('********** MainPageComponent.ts: ' + error);
      }
    )

    this.days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Saturday', 'Sunday'];

    this.types = ['VISA', 'MasterCard', 'Amex']; 

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

  deleteCreditCard(creditCardId: number)
  {
    this.creditCardId = creditCardId; 
    this.creditCardService.deleteCreditCard(this.creditCardId).subscribe(
      response => {
        this.router.navigate(["/mainPage"]); 
        this.creditCardService.getCreditCards().subscribe(
          response => {
            this.creditCards = response.creditCards; 
          }, 
          error => {
            console.log('********** MainPageComponent.ts: ' + error);
          }
        )
      }, 
      error => {
        this.error = true; 
        this.errorMessage = error; 
      }
    ); 
  }

  addCC(addCCForm: NgForm) {
    this.ccSubmitted = true; 

    this.newCreditCard.type = this.ccType; 
    this.newCreditCard.cardName = this.ccName; 
    this.newCreditCard.cardNumber = this.ccNum; 
    this.newCreditCard.expiryDate = this.ccDate; 

    if (addCCForm.valid) 
    {
      this.creditCardService.createCreditCard(this.newCreditCard).subscribe(
        response => {
          let newCCid: number = response.creditCardId; 
          this.resultSuccess = true;
          this.resultError = false; 
          this.message = "New Credit Card " + newCCid + " created successfully"; 
          this.creditCardService.getCreditCards().subscribe(
            response => {
              this.creditCards = response.creditCards; 
            }, 
            error => {
              console.log('********** MainPageComponent.ts: ' + error);
            }
          )
        }, 
        error => {
          this.resultError = true; 
          this.resultSuccess = false; 
          this.message = "An errror has occured while creating the new Credit Card: " + error; 

          console.log('********** MainPageComponent.ts: ' + error);
        }
      )
    }

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

  showAddCCDialog(serviceProvider: ServiceProvider) {
    this.displayAddCC = true; 
    this.serviceProvider = serviceProvider; 
  }
  
  closeAddCCDialog() {
    this.displayAddCC = false;
    this.displayCC = false;  
  }

}
