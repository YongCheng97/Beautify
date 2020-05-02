import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Review } from './review'; 



const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})


export class ReviewService {

  baseUrl: string = "/api/Review";

  constructor(private httpClient: HttpClient, 
    private sessionService: SessionService) 
  {

  }

  getProductReviews(): Observable<any>
  {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllProductReviews?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe 
    (
      catchError(this.handleError)
    ); 
  }

  getServiceReviews(): Observable<any>
  {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllServiceReviews?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe 
    (
      catchError(this.handleError)
    ); 
  }

  getReviewByReviewId(reviewId: number): Observable<any> 
  {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveReview/" + reviewId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe 
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
