import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm, Validators, FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { MessageService } from 'primeng/api';
import * as moment from 'moment';

import { ServiceProviderService } from '../service-provider.service';
import { FileUploadService } from '../file-upload.service';
import { ServiceProvider } from '../service-provider';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
  providers: [MessageService]
})
export class SignUpComponent implements OnInit {

  userform: FormGroup;

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

  showImage: Boolean = false;
  fileName: String = null;
  fileToUpload: File = null;


  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public serviceProviderService: ServiceProviderService,
    private fileUploadService: FileUploadService,
    private fb: FormBuilder,
    private messageService: MessageService) {
    this.submitted = false;
    this.newServiceProvider = new ServiceProvider();

    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit() {
    this.userform = this.fb.group({
      'name': new FormControl('', Validators.compose([Validators.required, Validators.maxLength(32)])),
      'username': new FormControl('', Validators.compose([Validators.required, Validators.maxLength(32)])),
      'email': new FormControl('', Validators.compose([Validators.required, Validators.email])),
      'password': new FormControl('', Validators.compose([Validators.required, Validators.maxLength(32)])),
      'address': new FormControl('', Validators.compose([Validators.required, Validators.maxLength(32)])),
      'mondayOpeningHrs': new FormControl(''),
      'mondayClosingHrs': new FormControl(''),
      'tuesdayOpeningHrs': new FormControl(''),
      'tuesdayClosingHrs': new FormControl(''),
      'wednesdayOpeningHrs': new FormControl(''),
      'wednesdayClosingHrs': new FormControl(''),
      'thursdayOpeningHrs': new FormControl(''),
      'thursdayClosingHrs': new FormControl(''),
      'fridayOpeningHrs': new FormControl(''),
      'fridayClosingHrs': new FormControl(''),
      'saturdayOpeningHrs': new FormControl(''),
      'saturdayClosingHrs': new FormControl(''),
      'sundayOpeningHrs': new FormControl(''),
      'sundayClosingHrs': new FormControl(''),
    });
  }

  createNewAccount(value: string) {
    this.submitted = true;

    console.log(value["name"]);

    this.newServiceProvider.name = value["name"];
    this.newServiceProvider.username = value["username"];
    this.newServiceProvider.email = value["email"];
    this.newServiceProvider.password = value["password"];
    this.newServiceProvider.address = value["address"];

    if (this.newServiceProvider.name == "") {
      this.newServiceProvider.name = null;
    } 
    if (this.newServiceProvider.username == "") {
      this.newServiceProvider.username = null;
    } 
    if (this.newServiceProvider.email == "") {
      this.newServiceProvider.email = null;
    } 
    if (this.newServiceProvider.password == "") {
      this.newServiceProvider.password = null;
    } 
    if (this.newServiceProvider.address == "") {
      this.newServiceProvider.address = null;
    } 

    this.newServiceProvider.openingHours = [];
    this.newServiceProvider.closingHours = [];

    this.newServiceProvider.openingHours.push(moment(value["mondayOpeningHrs"], "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(value["tuesdayOpeningHrs"], "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(value["wednesdayOpeningHrs"], "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(value["thursdayOpeningHrs"], "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(value["fridayOpeningHrs"], "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(value["saturdayOpeningHrs"], "HH:mm").toDate());
    this.newServiceProvider.openingHours.push(moment(value["sundayOpeningHrs"], "HH:mm").toDate());

    this.newServiceProvider.closingHours.push(moment(value["mondayClosingHrs"], "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(value["tuesdayClosingHrs"], "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(value["wednesdayClosingHrs"], "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(value["thursdayClosingHrs"], "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(value["fridayClosingHrs"], "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(value["saturdayClosingHrs"], "HH:mm").toDate());
    this.newServiceProvider.closingHours.push(moment(value["sundayClosingHrs"], "HH:mm").toDate());

    this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Form Submitted' });

    this.serviceProviderService.createNewServiceProvider(this.newServiceProvider).subscribe(
      response => {
        let newServiceProviderId: number = response.serviceProviderId;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "New service provider " + newServiceProviderId + " created successfully!";
        this.router.navigate(["/index"]);
      },
      error => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = error;
      }
    )
  }

  get diagnostic() { return JSON.stringify(this.userform.value); }
}
