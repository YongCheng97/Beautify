import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Product } from './product';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl: string = "/api/Product";

  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  getProducts(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllProducts?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }

  createProduct(newProduct: Product, categoryId: number, tagIds: number[]): Observable<any> {
    let createProductReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "product": newProduct,
      "categoryId": categoryId,
      "tagIds": tagIds
    }

    return this.httpClient.put<any>(this.baseUrl, createProductReq, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  updateProduct(productToUpdate: Product, categoryId: number, tagIds: number[]): Observable<any> {

    console.log(productToUpdate.productId);
    let updateBookReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "product": productToUpdate,
      "categoryId": categoryId,
      "tagIds": tagIds
    };

    return this.httpClient.post<any>(this.baseUrl, updateBookReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );
  }

  deleteProduct(productId: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseUrl + "/" + productId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
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
