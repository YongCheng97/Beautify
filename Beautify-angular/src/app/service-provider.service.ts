import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { ServiceProvider } from './service-provider';
import { CreditCard } from './credit-card';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
	providedIn: 'root'
})

export class ServiceProviderService {

	baseUrl: string = "/api/ServiceProvider";

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) {

	}

	login(username: string, password: string): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/serviceProviderLogin?username=" + username + "&password=" + password).pipe(
			catchError(this.handleError)
		)
	}

	createNewServiceProvider(newServiceProvider: ServiceProvider): Observable<any> {
		let createServiceProviderReq = {
			"serviceProvider": newServiceProvider,
		}

		return this.httpClient.put<any>(this.baseUrl + "/createServiceProvider", createServiceProviderReq, httpOptions).pipe(
			catchError(this.handleError)
		);
	}

	updateServiceProvider(providerToUpdate: ServiceProvider): Observable<any> {
		let updateProviderReq = {
			"username": this.sessionService.getUsername(),
			"password": this.sessionService.getPassword(),
			"serviceProvider": providerToUpdate,
		};

		return this.httpClient.post<any>(this.baseUrl + "/updateServiceProvider" , updateProviderReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}
	
	changePassword(newPassword: String): Observable<any> {
		let changeServiceProviderPasswordReq = {
			"username": this.sessionService.getUsername(),
			"password": this.sessionService.getPassword(),
			"serviceProvider": this.sessionService.getCurrentServiceProvider(),
			"newPassword": newPassword,
		};
		

		return this.httpClient.post<any>(this.baseUrl + "/changePassword" , changeServiceProviderPasswordReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}
	
	makePayment(creditCardUsed: CreditCard): Observable<any> {
		let createNewSalesForUsReq = {
			"username": this.sessionService.getUsername(),
			"password": this.sessionService.getPassword(),
			"serviceProvider": this.sessionService.getCurrentServiceProvider(),
			"creditCard": creditCardUsed,
		};
		

		return this.httpClient.put<any>(this.baseUrl + "/makePayment",createNewSalesForUsReq, httpOptions).pipe(
			catchError(this.handleError)
		);
	}

	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = "";

		if (error.error instanceof ErrorEvent) {
			errorMessage = error.error.message;
		}
		else {
			errorMessage = error.error.message + " Please try again!";
		}

		console.error(errorMessage);

		return throwError(errorMessage);
	}

}
