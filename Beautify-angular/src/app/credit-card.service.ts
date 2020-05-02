import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { CreditCard } from './credit-card'; 



const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})

export class CreditCardService {

  baseUrl: string = "/api/CreditCard";

  constructor(private httpClient: HttpClient, 
    private sessionService: SessionService)
  {

  }

  getCreditCards(): Observable<any> 
  {
    console.log("this runs"); 
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllCreditCards?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
  }

  createCreditCard(newCreditCard: CreditCard): Observable<any> 
  {
    console.log("create cc runs"); 
    let createCreditCardReq = {
      "username": this.sessionService.getUsername(), 
      "password": this.sessionService.getPassword(), 
      "creditCard": newCreditCard, 
    }; 

    return this.httpClient.put<any>(this.baseUrl, createCreditCardReq, httpOptions).pipe
		(
			catchError(this.handleError)
		);
  }

  deleteCreditCard(creditCardId: number): Observable<any>
  {
    console.log("delete cc runs"); 
    return this.httpClient.delete<any>(this.baseUrl + "/" + creditCardId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
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
