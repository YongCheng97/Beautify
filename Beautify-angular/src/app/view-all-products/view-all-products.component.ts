import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { ProductService } from '../product.service';
import { CategoryService } from '../category.service';
import { FileUploadService } from '../file-upload.service';
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
  productToView: Product;
  productToDelete: Product;
  productToUpdate: Product;
  productToUpload: Product;
  serviceProvider: ServiceProvider;
  displayAdd: boolean = false;
  displayView: boolean = false;
  displayUpdate: boolean = false;
  displayDelete: boolean = false;
  displayPhoto: boolean = false;
  submitted: boolean;

  categoryId: number;
  selectedCategory: Category;
  updatedCategory: Category;
  tagIds: string[];
  categories: Category[];
  categoryNames: string[];
  selectedTags: Tag[];
  selectUpdatedTags: Tag[];
  tags: Tag[];

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  showImage: Boolean = false;
  fileName: String = null;
  fileToUpload: File = null;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private productService: ProductService,
    private categoryService: CategoryService,
    private tagService: TagService,
    private fileUploadService: FileUploadService) {

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

  handlePrimeNgFileInput(event) {
    this.fileToUpload = event.files[0];

    this.fileUploadService.uploadFile(this.fileToUpload).subscribe(
      response => {
        this.fileName = this.fileToUpload.name;
        this.showImage = true;
        console.log('********** FileUploadComponent.ts: File uploaded successfully: ' + response.status);
        this.displayPhoto = false;
      },
      error => {
        console.log('********** FileUploadComponent.ts: ' + error);
      }
    );
  }

  showDialog() {
    this.displayAdd = true;
  }

  showViewDialog(productToView: Product) {
    this.displayView = true;
    this.productToView = productToView;
  }

  showUpdateDialog(productToUpdate: Product) {
    this.displayUpdate = true;
    this.productToUpdate = productToUpdate;
  }

  showDeleteDialog(productToDelete: Product) {
    this.displayDelete = true;
    this.productToDelete = productToDelete;
  }

  showPhotoDialog(productToUpload: Product) {
    this.displayPhoto = true;
    this.productToUpload = productToUpload;
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
        this.displayAdd = false;
        this.productService.getProducts().subscribe(
          response => {
            this.products = response.products;
          },
          error => {
            console.log('********** ViewAllProductsComponent.ts: ' + error);
          }
        );
      },
      error => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = "An error has occurred while creating the new product: " + error;

        console.log('********** CreateNewProductComponent.ts: ' + error);
      }
    );
    // }
  }

  update(updateProductForm: NgForm) {

    this.categoryId = this.updatedCategory.categoryId;
    let longTagIds: number[] = new Array();

    for (var i = 0; i < this.selectUpdatedTags.length; i++) {
      longTagIds.push(this.selectUpdatedTags[i].tagId);
    }

    this.submitted = true;

    // if (updateProductForm.valid) {
    this.productService.updateProduct(this.productToUpdate, this.categoryId, longTagIds).subscribe(
      response => {
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "Product updated successfully";
        this.displayUpdate = false;
        this.productService.getProducts().subscribe(
          response => {
            this.products = response.products;
          },
          error => {
            console.log('********** ViewAllProductsComponent.ts: ' + error);
          }
        );
      },
      error => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = "An error has occurred while updating the product: " + error;

        console.log('********** UpdateProductComponent.ts: ' + error);
      }
    );
    // }
  }

  delete(deleteProductForm: NgForm) {
    this.productService.deleteProduct(this.productToDelete.productId).subscribe(
      response => {
        this.router.navigate(["/view-all-products"]);
        this.displayDelete = false;
        this.productService.getProducts().subscribe(
          response => {
            this.products = response.products;
          },
          error => {
            console.log('********** ViewAllProductsComponent.ts: ' + error);
          }
        );
      },
      error => {
        console.log('********** DeleteServiceComponent.ts: ' + error);
      }
    );
  }
}

