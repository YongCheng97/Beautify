import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ServiceProviderService } from '../service-provider.service';
import { ServiceProvider } from '../service-provider';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

	@Output() 
	childEvent = new EventEmitter();

  username: string;
  password: string;
  loginError: boolean;
  errorMessage: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    public serviceProviderService: ServiceProviderService) {
      this.loginError = false;
  }

  ngOnInit() {
	  this.sessionService.setUsername(null);
	  this.sessionService.setPassword(null);
	  this.sessionService.setIsLogin(false);
	  this.sessionService.setCurrentServiceProvider(null);
  }

  login(): void {
    this.sessionService.setUsername(this.username);
    this.sessionService.setPassword(this.password);

    this.serviceProviderService.login(this.username, this.password).subscribe(
      response => {
        let serviceProvider: ServiceProvider = response.serviceProvider;

        if (serviceProvider != null) {
          this.sessionService.setIsLogin(true);
          this.sessionService.setCurrentServiceProvider(serviceProvider);
          console.log(serviceProvider.name);
          sessionStorage.setItem('serviceProvider', JSON.stringify(serviceProvider));
          this.loginError = false;

          this.childEvent.emit();

          this.router.navigate(["/main-page"]);
        } else {
          this.loginError = true;
        }
      },
      error => {
        this.loginError = true;
        this.errorMessage = error;
      }
    );
  }

  goToSignUpPage() {
    this.router.navigate(["/sign-up"])
  }

}