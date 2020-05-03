import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Promotion } from './promotion'; 

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})


export class PromotionService {

  baseUrl: string = "/api/Promotion";

  constructor(private httpClient: HttpClient, 
    private sessionService: SessionService)
  {
  }

  getProductPromotions(): Observable<any> 
  {
    console.log("this runs"); 
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllProductPromotions?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
  }

  getServicePromotions(): Observable<any> 
  {
    console.log("this runs"); 
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllServicePromotions?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
  }

  createPromotion(newPromotion: Promotion, serviceId: number, productId: number): Observable<any> 
  {
    console.log("create promo runs"); 
    let createPromotionReq = {
      "username": this.sessionService.getUsername(), 
      "password": this.sessionService.getPassword(), 
      "promotion": newPromotion,
      "serviceId": serviceId,
      "productId": productId
    }; 

    return this.httpClient.put<any>(this.baseUrl, createPromotionReq, httpOptions).pipe
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
