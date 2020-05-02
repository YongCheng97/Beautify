import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { ProductService } from '../product.service';
import { CategoryService } from '../category.service';
import { TagService } from '../tag.service';
import { ServiceProvider } from '../service-provider';
import { Product } from '../product';
import { Tag } from '../tag';
import { Category } from '../category';

@Component({
  selector: 'app-view-all-products',
  templateUrl: './view-all-products.component.html',
  styleUrls: ['./view-all-products.component.css']
})
export class ViewAllProductsComponent implements OnInit {

  products: Product[];
  newProduct: Product;
  serviceProvider: ServiceProvider;
  displayAdd: boolean = false;
  submitted: boolean;

  categoryId: number;
  selectedCategory: Category;
  tagIds: string[];
  categories: Category[];
  categoryNames: string[];
  selectedTags: Tag[];
  tags: Tag[];

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private productService: ProductService,
    private categoryService: CategoryService,
    private tagService: TagService) {

    this.submitted = false;
    this.newProduct = new Product();

    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit() {
    this.serviceProvider = this.sessionService.getCurrentServiceProvider();

    this.categoryService.getProductCategories().subscribe(
      response => {
        this.categories = response.categories;
      },
      error => {
        console.log('********** CreateNewProductComponent.ts: ' + error);
      }
    );

    this.productService.getProducts().subscribe(
      response => {
        this.products = response.products;
      },
      error => {
        console.log('********** ViewAllProductsComponent.ts: ' + error);
      }
    );

    this.tagService.getTags().subscribe(
      response => {
        this.tags = response.tags;
      },
      error => {
        console.log('********** CreateNewProductComponent.ts: ' + error);
      }
    );
  }

  showDialog() {
    this.displayAdd = true;
  }

  clear() {
    this.submitted = false;
    this.newProduct = new Product();
  }

  create(addProductForm: NgForm) {

    this.categoryId = this.selectedCategory.categoryId;

    let longTagIds: number[] = new Array();

    for (var i = 0; i < this.selectedTags.length; i++) {
      longTagIds.push(this.selectedTags[i].tagId);
    }

    this.submitted = true;

    // if (addProductForm.valid) {
      this.productService.createProduct(this.newProduct, this.categoryId, longTagIds).subscribe(
        response => {
          let newProductId: number = response.productId;
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "New product " + newProductId + " created successfully";
        },
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occurred while creating the new product: " + error;

          console.log('********** CreateNewProductComponent.ts: ' + error);
        }
      );
    // }
    this.displayAdd = false;
  }
}

