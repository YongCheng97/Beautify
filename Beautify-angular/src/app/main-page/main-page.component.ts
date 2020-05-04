import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { SessionService } from '../session.service';
import { ServiceProviderService } from '../service-provider.service';
import { ServiceProvider } from '../service-provider';
import { CreditCard } from '../credit-card'; 
import { CreditCardService } from '../credit-card.service'; 
import { Storehour } from '../storehour'; 

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
  
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
  
  selectedCreditCard: CreditCard;

  displayName: boolean = false;
  displayEmail: boolean = false;
  displayAddress: boolean = false;
  displayHours: boolean = false;
  displayEditHours: boolean = false; 
  storeHours: Storehour[]; 
  openingHours: Date[]; 
  closingHours: Date[]; 
  displayCC: boolean = false; 
  displayChangePW: boolean = false;
  displayMakePayment: boolean = false;

  nameSubmitted: boolean;
  newName: string
  emailSubmitted: boolean;
  newEmail: string
  addressSubmitted: boolean;
  newAddress: string
  hoursSubmitted: boolean; 

  mondayOpeningHour: Date;
  tuesdayOpeningHour: Date;
  wednesdayOpeningHour: Date;
  thursdayOpeningHour: Date;
  fridayOpeningHour: Date;
  saturdayOpeningHour: Date;
  sundayOpeningHour: Date;

  mondayClosingHour: Date;
  tuesdayClosingHour: Date;
  wednesdayClosingHour: Date;
  thursdayClosingHour: Date;
  fridayClosingHour: Date;
  saturdayClosingHour: Date;
  sundayClosingHour: Date;

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
    this.hoursSubmitted = false; 
    this.resultSuccess = false;
    this.resultError = false;

    this.newCreditCard = new CreditCard(); 

    this.serviceProviderToUpdate = new ServiceProvider(); 

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

    this.storeHours = [] as Storehour[]; 
    this.openingHours = this.sessionService.getCurrentServiceProvider().openingHours;
    this.closingHours = this.sessionService.getCurrentServiceProvider().closingHours;
    let days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

    for (var i = 0; i < this.openingHours.length; i++) {
      let hr: Storehour = new Storehour(); 

      hr.day = days[i]; 
      hr.openHour = this.openingHours[i]; 
      hr.closeHour = this.closingHours[i]; 

      this.storeHours.push(hr);   
    }

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
      this.displayName = false; 
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
      this.displayEmail = false; 
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
      this.displayAddress = false; 
    }
  }

  deleteCreditCard(creditCardId: number)
  {
    this.creditCardId = creditCardId; 
    this.creditCardService.deleteCreditCard(this.creditCardId).subscribe(
      response => {
        this.router.navigate(["/main-page"]); 
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
      this.displayAddCC = false; 
    }
  }

  editHours(editHoursForm: NgForm) {
    this.hoursSubmitted = true;

    if (editHoursForm.valid) {

      this.serviceProviderToUpdate = this.sessionService.getCurrentServiceProvider(); 

      this.serviceProviderToUpdate.openingHours = [];
      this.serviceProviderToUpdate.closingHours = [];

      this.serviceProviderToUpdate.openingHours.push(moment(this.mondayOpeningHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.openingHours.push(moment(this.tuesdayOpeningHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.openingHours.push(moment(this.wednesdayOpeningHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.openingHours.push(moment(this.thursdayOpeningHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.openingHours.push(moment(this.fridayOpeningHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.openingHours.push(moment(this.saturdayOpeningHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.openingHours.push(moment(this.sundayOpeningHour, "HH:mm").toDate());

      this.serviceProviderToUpdate.closingHours.push(moment(this.mondayClosingHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.closingHours.push(moment(this.tuesdayClosingHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.closingHours.push(moment(this.wednesdayClosingHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.closingHours.push(moment(this.thursdayClosingHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.closingHours.push(moment(this.fridayClosingHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.closingHours.push(moment(this.saturdayClosingHour, "HH:mm").toDate());
      this.serviceProviderToUpdate.closingHours.push(moment(this.sundayClosingHour, "HH:mm").toDate());

      this.serviceProviderService.updateServiceProvider(this.serviceProviderToUpdate).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = true;
          this.message = "Opening and Closing Hours updated successfully";
          this.sessionService.setCurrentServiceProvider(this.serviceProviderToUpdate);
          
          this.storeHours = [] as Storehour[]; 
          this.openingHours = this.sessionService.getCurrentServiceProvider().openingHours;
          this.closingHours = this.sessionService.getCurrentServiceProvider().closingHours;
          let days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
      
          for (var i = 0; i < this.openingHours.length; i++) {
            let hr: Storehour = new Storehour(); 
      
            hr.day = days[i]; 
            hr.openHour = this.openingHours[i]; 
            hr.closeHour = this.closingHours[i]; 
      
            this.storeHours.push(hr);   
          }

        },
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occured while updating the opening and closing hours: " + error;

          console.log('********** MainPageComponent.ts: ' + error);
        }
      )
      this.displayEditHours = false; 
    }
  }
  
  changePassword(changePWForm: NgForm) {
	  
	  if (changePWForm.valid) {
			
			if (this.currentPassword==this.sessionService.getPassword() && this.newPassword==this.confirmPassword){

			  this.serviceProviderToUpdate.password = this.confirmPassword;
			  
			  this.serviceProviderService.changePassword(this.serviceProviderToUpdate.password).subscribe(
				response => {
				  this.resultSuccess = true;
				  this.resultError = true;
				  this.message = "Password updated successfully";
				  this.sessionService.setCurrentServiceProvider(this.serviceProviderToUpdate);
				},
				error => {
				  this.resultError = true;
				  this.resultSuccess = false;
				  this.message = "An error has occured while updating the password: " + error;

				  console.log('********** MainPageComponent.ts: ' + error);
				}
			  );
			  
			  this.sessionService.setPassword(this.serviceProviderToUpdate.password);
			  
			this.displayChangePW = false;  			
		  } else {
			  this.message = "Password does not match";
			  
		  }
	  }
  }
  
  makePayment(makePaymentForm: NgForm) {
	  
	  if(makePaymentForm.valid) {
		  
		  this.serviceProviderService.makePayment(this.selectedCreditCard).subscribe(
			response => {
				  this.resultSuccess = true;
				  this.resultError = true;
				  this.message = "Payment made successfully";
				},
				error => {
				  this.resultError = true;
				  this.resultSuccess = false;
				  this.message = "An error has occured while making payment: " + error;

				  console.log('********** MainPageComponent.ts: ' + error);
				}
			  );
		this.displayMakePayment = false;
	  }
  }
  

  showNameDialog(serviceProvider: ServiceProvider) {
    this.displayName = true;
    this.serviceProviderToUpdate = serviceProvider;
  }

  showEmailDialog(serviceProvider: ServiceProvider) {
    this.displayEmail = true;
    this.serviceProviderToUpdate = serviceProvider;
  }

  showAddressDialog(serviceProvider: ServiceProvider) {
    this.displayAddress = true;
    this.serviceProviderToUpdate = serviceProvider;
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

  showEditHoursDialog(serviceProvider: ServiceProvider) {
    this.displayEditHours = true; 
    this.serviceProvider = serviceProvider; 
  }
  
  showPasswordDialog(serviceProvider: ServiceProvider) {
    this.displayChangePW = true;
    this.serviceProviderToUpdate = serviceProvider;
	this.currentPassword = null;
	this.newPassword = null;
	this.confirmPassword = null;
  }
  
  showMakePaymentDialog(serviceProvider: ServiceProvider) {
    this.displayMakePayment = true; 
    this.serviceProvider = serviceProvider; 
  }

  parseDate(d: Date)
	{		
    if (d != null) {
      return d.toString().replace('[UTC]', '');
    }
  }

}
