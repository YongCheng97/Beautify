import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { CategoryService } from '../category.service';
import { Category } from '../category';
import { CategoryTypeEnum } from '../category-type-enum.enum';

@Component({
  selector: 'app-view-all-product-categories',
  templateUrl: './view-all-product-categories.component.html',
  styleUrls: ['./view-all-product-categories.component.css']
})
export class ViewAllProductCategoriesComponent implements OnInit {

	productCategories: Category[];
	newProductCategory: Category;
	productCategoryToDelete: Category;
	cols: any[];
	
	rootCategories: Category[];
	rootCategory: Category;
	
	newCategoryName: string;
	newCategoryDesc: string;
	newCategoryType: string;
	newCategoryParentId: number;
	
	submitted: boolean;
	resultSuccess: boolean;
	resultError: boolean;
	message: string;
	
	displayCreate: boolean = false;
	displayDelete: boolean = false;

	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private categoryService: CategoryService,
				) 
				{ 
					this.newProductCategory = new Category();
					this.submitted = false;
					this.resultSuccess = false;
					this.resultError = false;
					this.newCategoryParentId = null;
					this.newCategoryType = "PRODUCT";
				}
	
	ngOnInit(): void {
		this.categoryService.getProductCategories().subscribe(
		response => {
			this.productCategories = response.categories;
		},
		error => {
			console.log('********** ViewAllProductCategoriesComponent.ts: ' + error);
		}
		)
		
		this.categoryService.getRootCategories().subscribe(
			response => {
				this.rootCategories = response.categories;
			},
			error => {
				console.log('********** ViewAllProductCategoriesComponent.ts: ' + error);
			}
		)
		
		this.cols = [
		{ field: 'categoryId', header:'Category Id'},
		{ field: 'name', header:'Category name'},
		{ field: 'description', header:'Category description'},
		{ field: 'type', header:'Category type'},
		];
		
		
	}
	clear() {
		this.submitted = false;
		this.newProductCategory = new Category();
	}
	
	showCreateDialog() {
		this.displayCreate = true;
	}
	
	showDeleteDialog(productCategoryToDelete: Category) {
		this.displayDelete = true;
		this.productCategoryToDelete = productCategoryToDelete;
	}
	
	create(createCategoryForm : NgForm) {
		this.submitted = true;
		this.newProductCategory.name = this.newCategoryName;
		this.newProductCategory.description = this.newCategoryDesc;
		this.newProductCategory.type = null;
		this.newCategoryParentId = this.rootCategory.categoryId;
		
		if(createCategoryForm.valid) {
			this.categoryService.createCategory(this.newProductCategory, this.newCategoryType, this.newCategoryParentId).subscribe(
				response => {
					let newProductCategoryId = response.categoryId;
					this.resultSuccess = true;
					this.resultError = false;
					this.message = "New service category " + newProductCategoryId + " created successfully";
					this.categoryService.getProductCategories().subscribe(
						response => {
							this.productCategories = response.categories;
						},
						error => {
							console.log('********** ViewAllProductCategoriesComponent.ts: ' + error);
						}
					)
				},
				error => {
					this.resultSuccess = false;
					this.resultError = true;
					this.message = "An error has occured while created the new category";
					console.log('********** ViewAllProductCategoriesComponent.ts: ' + error);
				}
			)
		}
		this.displayCreate = false;
	}
	
	deleteCategory(deleteCategoryForm: NgForm) {
		this.categoryService.deleteCategory(this.productCategoryToDelete.categoryId).subscribe(
			response => {
				this.router.navigate(["/view-all-product-categories"]);
		
				this.categoryService.getProductCategories().subscribe(
					response => {
						this.productCategories = response.categories;
					},
					error => {
						console.log('********** ViewAllProductCategoriesComponent.ts: ' + error);
					}
				)
			},
			error => {
				console.log('********** DeleteServiceComponent.ts: ' + error);
			}
		);
		this.displayDelete = false;
	}

}
