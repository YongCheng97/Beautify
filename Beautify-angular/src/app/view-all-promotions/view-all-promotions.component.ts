import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';

import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { PromotionService } from '../promotion.service';
import { Promotion } from '../promotion';
import { Product } from '../product';
import { Service } from '../service';
import { ServiceProvider } from '../service-provider';
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

  updateProductId: number;
  updateServiceId: number;

  serviceProvider: ServiceProvider;
  productPromotionToUpdate: Promotion;
  servicePromotionToUpdate: Promotion;
  displayUpdateProductPromo: boolean = false;
  displayUpdateServicePromo: boolean = false;

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
    private serviceService: ServiceService) {
    this.error = false;

    this.newPromotion = new Promotion();
  }

  ngOnInit() {

    this.serviceProvider = this.sessionService.getCurrentServiceProvider();

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
      { field: 'discountRate', header: 'Discount Rate' },
      { field: 'startDate', header: 'Start Date' },
      { field: 'endDate', header: 'End Date' },
      { field: 'product.name', header: 'Product Name' },
      { field: '', }
    ]

    this.cols1 = [
      { field: 'promotionId', header: 'Promotion ID' },
      { field: 'promoCode', header: 'Code' },
      { field: 'name', header: 'Name' },
      { field: 'discountRate', header: 'Discount Rate' },
      { field: 'startDate', header: 'Start Date' },
      { field: 'endDate', header: 'End Date' },
      { field: 'service.serviceName', header: 'Service Name' },
      { field: '', }
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

    if (addProductPromoForm.valid) {
      this.promotionService.createPromotion(this.newPromotion, this.newServiceId, this.newProductId).subscribe(
        response => {
          let newPromoId: number = response.promoId;
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "New Product Promotion " + newPromoId + " created successfully!";
          this.displayAddProductPromo = false;
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
  }

  addServicePromo(addServicePromoForm: NgForm) {
    this.promoSubmitted = true;

    this.newServiceId = this.newService.serviceId;
    this.newProductId = null;

    this.newPromotion.name = this.newPromoName;
    this.newPromotion.promoCode = this.newPromoCode;
    this.newPromotion.discountRate = this.newDiscountRate;
    this.newPromotion.startDate = moment(this.newStartDate, "dd/MM/yy").toDate();
    this.newPromotion.endDate = moment(this.newEndDate, "dd/MM/yy").toDate();

    if (addServicePromoForm.valid) {
      this.promotionService.createPromotion(this.newPromotion, this.newServiceId, this.newProductId).subscribe(
        response => {
          let newPromoId: number = response.promoId;
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "New service Promotion " + newPromoId + " created successfully!";
          this.displayAddServicePromo = false;
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
  }

  deletePromotion(promotionId: number) {
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

  showUpdateProductDialog(productPromotionToUpdate: Promotion) {
    this.displayUpdateProductPromo = true;
    this.productPromotionToUpdate = productPromotionToUpdate;
  }

  showUpdateServiceDialog(servicePromotionToUpdate: Promotion) {
    this.displayUpdateServicePromo = true;
    this.servicePromotionToUpdate = servicePromotionToUpdate;
  }

  updateProductPromo(updateProductPromoForm: NgForm) {

    this.newProductId = this.productPromotionToUpdate.product.productId;
    this.newServiceId = null;

    this.productPromotionToUpdate.startDate = moment(this.newStartDate, "dd/MM/yy").toDate();
    this.productPromotionToUpdate.endDate = moment(this.newEndDate, "dd/MM/yy").toDate();

    if (updateProductPromoForm.valid) {
      this.promotionService.updatePromotion(this.productPromotionToUpdate, this.newServiceId, this.newProductId).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "Service promotion updated successfully!";
          this.displayUpdateProductPromo = false;
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
  }

  updateServicePromo(updateServicePromoForm: NgForm) {

    this.newServiceId = this.newService.serviceId;
    this.newProductId = null;

    this.servicePromotionToUpdate.startDate = moment(this.newStartDate, "dd/MM/yy").toDate();
    this.servicePromotionToUpdate.endDate = moment(this.newEndDate, "dd/MM/yy").toDate();

    if (updateServicePromoForm.valid) {
      this.promotionService.updatePromotion(this.servicePromotionToUpdate, this.newServiceId, this.newProductId).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "Service promotion updated successfully!";
          this.displayUpdateServicePromo = false;
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

  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]', '');
  }

}
