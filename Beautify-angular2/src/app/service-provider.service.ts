import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { ServiceProvider } from './service-provider';



const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};



@Injectable({
  providedIn: 'root'
})

export class ServiceProviderService 
{
	baseUrl: string = "/api/ServiceProvider";
	
	
	
	constructor(private httpClient: HttpClient,
				private sessionService: SessionService)
	{		
	}
	
	
	
	getServiceProviders(): Observable<any>
	{				
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllServiceProviders?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}
	
	
	
	getServiceProviderByServiceProviderId(serviceProviderId: number): Observable<any>
	{
		return this.httpClient.get<any>(this.baseUrl + "/retrieveServiceProvider/" + serviceProviderId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
				
	}	
	
	
	updateServiceProvider(serviceProviderToUpdate: ServiceProvider): Observable<any>
	{
		let updateServiceProviderReq = {
			"username": this.sessionService.getUsername(),
			"password": this.sessionService.getPassword(),
			"serviceProvider": serviceProviderToUpdate,
		};
		return this.httpClient.post<any>(this.baseUrl + "/updateServiceProviderStatus", updateServiceProviderReq, httpOptions).pipe
		(
			catchError(this.handleError)
		);
	}
	
	
	
	private handleError(error: HttpErrorResponse)
	{
		let errorMessage: string = "";
		
		if (error.error instanceof ErrorEvent) 
		{		
			errorMessage = "An unknown error has occurred: " + error.error.message;
		} 
		else 
		{		
			errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
		}
		
		console.error(errorMessage);
		
		return throwError(errorMessage);		
	}
}
