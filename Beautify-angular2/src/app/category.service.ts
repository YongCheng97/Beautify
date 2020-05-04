import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Category } from './category';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl: string = "/api/Category";

  constructor(private httpClient: HttpClient,
    private sessionService: SessionService) {
  }
  
  getRootCategories(): Observable<any> {
	 return this.httpClient.get<any>(this.baseUrl + "/retrieveAllRootCategoriesForStaff?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      ); 
  }

  getProductCategories(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllProductCategoriesForStaff?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }

  getServiceCategories(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllServiceCategoriesForStaff?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }
  
  createCategory(newCategory: Category, categoryType: string, categoryId: number): Observable<any>
  {
	  let createCategoryReq = {
		  "username": this.sessionService.getUsername(),
		  "password": this.sessionService.getPassword(),
		  "newCategory": newCategory,
		  "categoryType": categoryType,
		  "categoryId": categoryId
		  };
	  
	  return this.httpClient.put<any>(this.baseUrl, createCategoryReq, httpOptions).pipe
	  (
		catchError(this.handleError)
	  );
  }
  
  deleteCategory(categoryId: number): Observable<any>
  {
	  return this.httpClient.delete<any>(this.baseUrl + "/" + categoryId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
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
