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

    console.log(typeof this.mondayOpeningHour);
    this.newServiceProvider.openingHours = [];
    this.newServiceProvider.closingHours = [];

    this.newServiceProvider.openingHours.push(new Date(this.mondayOpeningHour));
    this.newServiceProvider.openingHours.push(new Date(this.tuesdayOpeningHour));
    this.newServiceProvider.openingHours.push(new Date(this.wednesdayOpeningHour));
    this.newServiceProvider.openingHours.push(new Date(this.thursdayOpeningHour));
    this.newServiceProvider.openingHours.push(new Date(this.fridayOpeningHour));
    this.newServiceProvider.openingHours.push(new Date(this.saturdayOpeningHour));
    this.newServiceProvider.openingHours.push(new Date(this.sundayOpeningHour));

    this.newServiceProvider.closingHours.push(new Date(this.mondayClosingHour));
    this.newServiceProvider.closingHours.push(new Date(this.tuesdayClosingHour));
    this.newServiceProvider.closingHours.push(new Date(this.wednesdayClosingHour));
    this.newServiceProvider.closingHours.push(new Date(this.thursdayClosingHour));
    this.newServiceProvider.closingHours.push(new Date(this.fridayClosingHour));
    this.newServiceProvider.closingHours.push(new Date(this.saturdayClosingHour));
    this.newServiceProvider.closingHours.push(new Date(this.sundayClosingHour));

   

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
