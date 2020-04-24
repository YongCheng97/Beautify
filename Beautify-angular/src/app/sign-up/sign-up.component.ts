import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

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

    // if (createServiceProviderForm.valid) {
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
    }
  }
// }
