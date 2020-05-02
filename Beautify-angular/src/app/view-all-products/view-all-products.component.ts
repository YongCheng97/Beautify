import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-view-all-products',
  templateUrl: './view-all-products.component.html',
  styleUrls: ['./view-all-products.component.css']
})
export class ViewAllProductsComponent implements OnInit {

  products: Product[];

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private productService: ProductService) { }

  ngOnInit() {
    this.productService.getProducts().subscribe(
      response => {
        this.products = response.products;
      },
      error => {
        console.log('********** ViewAllProductsComponent.ts: ' + error);
      }
    );
    console.log(this.products);
  }

}
