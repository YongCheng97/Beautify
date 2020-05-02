import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ReviewService } from '../review.service';
import { Review } from '../review';



@Component({
  selector: 'app-view-all-reviews',
  templateUrl: './view-all-reviews.component.html',
  styleUrls: ['./view-all-reviews.component.css']
})


export class ViewAllReviewsComponent implements OnInit {

  productReviews: Review[];
  serviceReviews: Review[];
  displayProduct: boolean = false;
  displayService: boolean = false;
  productReviewToView: Review;
  serviceReviewToView: Review;

  constructor(public sessionService: SessionService,
    private reviewService: ReviewService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
  }


  ngOnInit() {
    this.reviewService.getServiceReviews().subscribe(
      response => {
        this.serviceReviews = response.reviews;
      },
      error => {
        console.log('********** ViewAllReviewsComponent.ts: ' + error);
      }
    );

    this.reviewService.getProductReviews().subscribe(
      response => {
        this.productReviews = response.reviews;
      },
      error => {
        console.log('********** ViewAllReviewsComponent.ts: ' + error);
      }
    );
  }

  showServiceDialog(serviceReviewToView: Review) {
    this.displayService = true;
    this.serviceReviewToView = serviceReviewToView;
  }

  showProductDialog(productReviewToView: Review) {
    this.displayProduct = true;
    this.productReviewToView = productReviewToView;
  }

  parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
  }

}
