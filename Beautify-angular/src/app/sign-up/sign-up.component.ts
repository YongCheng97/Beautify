import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { ServiceProviderService } from '../service-provider.service';
import { ServiceProvider } from '../service-provider';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  submitted: boolean;
  newServiceProvider: ServiceProvider;

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

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public serviceProviderService: ServiceProviderService) {
    this.submitted = false;
    this.newServiceProvider = new ServiceProvider();

    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit() {
  }

  createNewAccount(createServiceProviderForm: NgForm) {
    this.submitted = true;

    //

    this.newServiceProvider.openingHours = [];
    this.newServiceProvider.closingHours = [];

    this.newServiceProvider.openingHours.push(moment(this.mondayOpeningHour, "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(this.tuesdayOpeningHour, "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(this.wednesdayOpeningHour, "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(this.thursdayOpeningHour, "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(this.fridayOpeningHour, "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(this.saturdayOpeningHour, "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(this.sundayOpeningHour, "HH:mm").toDate());

    this.newServiceProvider.closingHours.push(moment(this.mondayClosingHour, "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(this.tuesdayClosingHour, "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(this.wednesdayClosingHour, "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(this.thursdayClosingHour, "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(this.fridayClosingHour, "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(this.saturdayClosingHour, "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(this.sundayClosingHour, "HH:mm").toDate());

    //  if (createServiceProviderForm.valid) {
    this.serviceProviderService.createNewServiceProvider(this.newServiceProvider).subscribe(
      response => {
        let newServiceProviderId: number = response.serviceProviderId;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "New service provider " + newServiceProviderId + " created successfully!";
        this.router.navigate(["/main-page"]);
      },
      error => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = error;
      }
    )
    // }
  }
}
