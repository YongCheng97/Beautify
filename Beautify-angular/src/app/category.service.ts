import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Category } from './category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl: string = "/api/Category";

  constructor(private httpClient: HttpClient,
    private sessionService: SessionService) {
  }

  getProductCategories(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllProductCategories?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }

  getServiceCategories(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllServiceCategories?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = "";

    if (error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error has occurred: " + error.error.message;
    }
    else {
      errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
    }

    console.error(errorMessage);

    return throwError(errorMessage);
  }
}
