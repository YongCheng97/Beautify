import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

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
  promotions: Promotion[]; 
  promotionToView: Promotion; 

  products: Product[]; 
  services: Service[];

  displayAddPromo: boolean = false;
  newPromotion: Promotion; 
  newPromoCode: string; 
  newPromoName: string; 
  newDiscountRate: number; 
  newStartDate: Date; 
  newEndDate: Date; 

  promoSubmitted: boolean; 

  resultSuccess: boolean; 
  resultError: boolean; 
  message: string; 

  cols: any[]; 

  constructor(public sessionService: SessionService, 
    private promotionService: PromotionService,
    private router: Router,
    private activatedRouter: ActivatedRoute, 
    private productService: ProductService, 
    private serviceService: ServiceService) { }

  ngOnInit() {
    this.promotionService.getPromotions().subscribe(
      response => {
        this.promotions = response.promotions;
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
      { field: '', header: 'Product / Service Name' }, 
      { field: '', header: 'Add to Product / Service' }
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
        console.log("************* ViewAllPromotionsComponent.ts: " + error); 
      }
    )

  }

  showPromoDialog(promotionToView: Promotion) {
    this.displayPromotion = true; 
    this.promotionToView = promotionToView; 
  } 

  showAddPromoDialog() {
    this.displayAddPromo = true; 
  }

  addPromo(addPromoForm: NgForm) {
    this.promoSubmitted = true; 

    this.newPromotion.name = this.newPromoName;
    this.newPromotion.promoCode = this.newPromoCode; 
    this.newPromotion.discountRate = this.newDiscountRate; 
    this.newPromotion.startDate = this.newStartDate;
    this.newPromotion.endDate = this.newEndDate; 

    if (addPromoForm.valid) 
    {
      this.promotionService.createPromotion(this.newPromotion).subscribe(
        response => {
          let newPromoId: number = response.promoId; 
          this.resultSuccess = true; 
          this.resultError = false; 
          this.message = "New Promotion " + newPromoId + " created successfully!"; 
          this.promotionService.getPromotions().subscribe(
            response => {
              this.promotions = response.promotions; 
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

    this.displayAddPromo = false; 
  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]', '');
  }

}
