import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { PromotionService } from '../promotion.service';
import { Promotion } from '../promotion';
import { Product } from '../product'; 
import { Service } from '../service'; 
import { ProductService } from '../product.service'; 
import { ServiceService } from '../service.service'; 


@Component({
  selector: 'app-view-all-promotions',
  templateUrl: './view-all-promotions.component.html',
  styleUrls: ['./view-all-promotions.component.css']
})
export class ViewAllPromotionsComponent implements OnInit {

  displayPromotion: boolean = false; 
  productPromotions: Promotion[]; 
  servicePromotions: Promotion[]; 
  promotionToView: Promotion; 

  products: Product[]; 
  services: Service[];

  displayAddProductPromo: boolean = false;
  displayAddServicePromo: boolean = false;
  newProduct: Product; 
  newService: Service; 
  newProductId: number; 
  newServiceId: number; 
  newPromotion: Promotion; 
  newPromoCode: string; 
  newPromoName: string; 
  newDiscountRate: number; 
  newStartDate: Date; 
  newEndDate: Date; 

  promotionId: number; 

  promoSubmitted: boolean; 

  resultSuccess: boolean; 
  resultError: boolean; 
  message: string; 

  error: boolean; 
  errorMessage: string; 

  cols: any[]; 
  cols1: any[];

  constructor(public sessionService: SessionService, 
    private promotionService: PromotionService,
    private router: Router,
    private activatedRouter: ActivatedRoute, 
    private productService: ProductService, 
    private serviceService: ServiceService) 
    { 
      this.error = false; 

      this.newPromotion = new Promotion(); 

      this.newProduct = new Product(); 

      this.newService = new Service(); 
    }

  ngOnInit() {

    this.promotionService.getServicePromotions().subscribe(
      response => {
        this.servicePromotions = response.promotions;
      },
      error => {
        console.log("************* ViewAllPromotionsComponent.ts: " + error); 
      }
    )

    this.promotionService.getProductPromotions().subscribe(
      response => {
        this.productPromotions = response.promotions;
      },
      error => {
        console.log("************* ViewAllPromotionsComponent.ts: " + error); 
      }
    )

    this.cols = [
      { field: 'promotionId', header: 'Promotion ID' },
      { field: 'promoCode', header: 'Code' },
      { field: 'name', header: 'Name' },
      { field: 'discountRate', header: 'Discount Rate'},
      { field: 'startDate', header: 'Start Date'},
      { field: 'endDate', header: 'End Date'},
      { field: 'product.name', header: 'Product Name' }, 
      { field: '', header: 'Delete' }
    ]

    this.cols1 = [
      { field: 'promotionId', header: 'Promotion ID' },
      { field: 'promoCode', header: 'Code' },
      { field: 'name', header: 'Name' },
      { field: 'discountRate', header: 'Discount Rate'},
      { field: 'startDate', header: 'Start Date'},
      { field: 'endDate', header: 'End Date'},
      { field: 'service.serviceName', header: 'Service Name' }, 
      { field: '', header: 'Delete' }
    ]

    this.productService.getProducts().subscribe(
      response => {
        this.products = response.products; 
      }, 
      error => {
        console.log("************* ViewAllPromotionsComponent.ts: " + error); 
      }
    )

    this.serviceService.getServices().subscribe(
      response => {
        this.services = response.services;
      },
      error => {
        console.log('********** ViewAllProductsComponent.ts: ' + error);
      }
    );

  }

  showPromoDialog(promotionToView: Promotion) {
    this.displayPromotion = true; 
    this.promotionToView = promotionToView; 
  } 

  showAddProductPromoDialog() {
    this.displayAddProductPromo = true; 
  }

  showAddServicePromoDialog() {
    this.displayAddServicePromo = true; 
  }

  addProductPromo(addProductPromoForm: NgForm) {
    this.promoSubmitted = true; 

    this.newProductId = this.newProduct.productId; 
    this.newServiceId = null; 

    this.newPromotion.name = this.newPromoName;
    this.newPromotion.promoCode = this.newPromoCode; 
    this.newPromotion.discountRate = this.newDiscountRate; 
    this.newPromotion.startDate = moment(this.newStartDate, "dd/MM/yy").toDate();
    this.newPromotion.endDate = moment(this.newEndDate, "dd/MM/yy").toDate(); 

    if (addProductPromoForm.valid) 
    {
      this.promotionService.createPromotion(this.newPromotion, this.newServiceId, this.newProductId).subscribe(
        response => {
          let newPromoId: number = response.promoId; 
          this.resultSuccess = true; 
          this.resultError = false; 
          this.message = "New Product Promotion " + newPromoId + " created successfully!"; 
          this.promotionService.getProductPromotions().subscribe(
            response => {
              this.productPromotions = response.promotions; 
            }, 
            error => {
              console.log("***************** PromotionComponent.ts: " + error); 
            }
          )
        }, 
        error => {
          this.resultError = true; 
          this.resultSuccess = false; 
          this.message = "An error has occured while created the new promotion"; 
          
          console.log("*************** PromotionComponent.ts: " + error); 
        }
      )
    }

    this.displayAddProductPromo = false; 
  }

  addServicePromo(addServicePromoForm: NgForm) {
    this.promoSubmitted = true; 

    this.newServiceId = this.newService.serviceId; 
    this.newProductId = null;

    console.log(this.newProduct.productId); 

    this.newPromotion.name = this.newPromoName;
    this.newPromotion.promoCode = this.newPromoCode; 
    this.newPromotion.discountRate = this.newDiscountRate; 
    this.newPromotion.startDate = moment(this.newStartDate, "dd/MM/yy").toDate();
    this.newPromotion.endDate = moment(this.newEndDate, "dd/MM/yy").toDate(); 

    if (addServicePromoForm.valid) 
    {
      this.promotionService.createPromotion(this.newPromotion, this.newServiceId, this.newProductId).subscribe(
        response => {
          let newPromoId: number = response.promoId; 
          this.resultSuccess = true; 
          this.resultError = false; 
          this.message = "New service Promotion " + newPromoId + " created successfully!"; 
          this.promotionService.getServicePromotions().subscribe(
            response => {
              this.servicePromotions = response.promotions; 
            }, 
            error => {
              console.log("***************** PromotionComponent.ts: " + error); 
            }
          )
        }, 
        error => {
          this.resultError = true; 
          this.resultSuccess = false; 
          this.message = "An error has occured while created the new promotion"; 
          
          console.log("*************** PromotionComponent.ts: " + error); 
        }
      )

    }
    this.displayAddServicePromo = false; 
  }

  deletePromotion(promotionId: number)
  {
    this.promotionId = promotionId; 
    this.promotionService.deletePromotion(this.promotionId).subscribe(
      response => {
        this.router.navigate(["/view-all-promotions"]); 

        this.promotionService.getServicePromotions().subscribe(
          response => {
            this.servicePromotions = response.promotions; 
          }, 
          error => {
            console.log('********** ViewAllPromotionsComponent.ts: ' + error);
          }
        )

        this.promotionService.getProductPromotions().subscribe(
          response => {
            this.productPromotions = response.promotions; 
          }, 
          error => {
            console.log('********** ViewAllPromotionsComponent.ts: ' + error);
          }
        )

      }, 
      error => {
        this.error = true; 
        this.errorMessage = error; 
      }
    ); 
  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]', '');
  }

}
