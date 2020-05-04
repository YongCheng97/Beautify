import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { CategoryService } from '../category.service';
import { Category } from '../category';
import { CategoryTypeEnum } from '../category-type-enum.enum';

@Component({
  selector: 'app-view-all-service-categories',
  templateUrl: './view-all-service-categories.component.html',
  styleUrls: ['./view-all-service-categories.component.css']
})
export class ViewAllServiceCategoriesComponent implements OnInit {

	serviceCategories: Category[];
	newServiceCategory: Category;
	serviceCategoryToDelete: Category;
	cols: any[];
	
	newCategoryName: string;
	newCategoryDesc: string;
	newCategoryType: string;
	newCategoryParentId: number;
	
	rootCategories: Category[];
	rootCategory: Category;
	
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
					this.newServiceCategory = new Category();
					this.submitted = false;
					this.resultSuccess = false;
					this.resultError = false;
					this.newCategoryParentId = null;
					this.newCategoryType = "SERVICE";
				}
	
	ngOnInit(): void {
		this.categoryService.getServiceCategories().subscribe(
		response => {
			this.serviceCategories = response.categories;
		},
		error => {
			console.log('********** ViewAllServiceCategoriesComponent.ts: ' + error);
		}
		)
		
		this.categoryService.getRootCategories().subscribe(
			response => {
				this.rootCategories = response.categories;
			},
			error => {
				console.log('********** ViewAllServiceCategoriesComponent.ts: ' + error);
			}
		)
		
		this.cols = [
		{ field: 'categoryId', header:'Category Id'},
		{ field: 'name', header:'Category name'},
		{ field: 'description', header:'Category description'},
		{ field: 'type', header:'Category type'},
		{ feild: '',header:'Delete Category'}
		];
		
		
	}
	
	clear() {
		this.submitted = false;
		this.newServiceCategory = new Category();
	}
	
	showCreateDialog() {
		this.displayCreate = true;
	}
	
	showDeleteDialog(serviceCategoryToDelete: Category) {
		this.displayDelete = true;
		this.serviceCategoryToDelete = serviceCategoryToDelete;
	}
	
	create(createCategoryForm : NgForm) {
		this.submitted = true;
		this.newServiceCategory.name = this.newCategoryName;
		this.newServiceCategory.description = this.newCategoryDesc;
		this.newServiceCategory.type = null;
		this.newCategoryParentId = this.rootCategory.categoryId;
		
		if(createCategoryForm.valid) {
			this.categoryService.createCategory(this.newServiceCategory, this.newCategoryType, this.newCategoryParentId).subscribe(
				response => {
					let newServiceCategoryId = response.categoryId;
					this.resultSuccess = true;
					this.resultError = false;
					this.message = "New service category " + newServiceCategoryId + " created successfully";
					this.categoryService.getServiceCategories().subscribe(
						response => {
							this.serviceCategories = response.categories;
						},
						error => {
							console.log('********** ViewAllServiceCategoriesComponent.ts: ' + error);
						}
					)
				},
				error => {
					this.resultSuccess = false;
					this.resultError = true;
					this.message = "An error has occured while created the new category";
					console.log('********** ViewAllServiceCategoriesComponent.ts: ' + error);
				}
			)
		}
		this.displayCreate = false;
	}
	
	deleteCategory(deleteCategoryForm: NgForm) {
		this.categoryService.deleteCategory(this.serviceCategoryToDelete.categoryId).subscribe(
			response => {
				this.router.navigate(["/view-all-service-categories"]);
		
				this.categoryService.getServiceCategories().subscribe(
					response => {
						this.serviceCategories = response.categories;
					},
					error => {
						console.log('********** ViewAllServiceCategoriesComponent.ts: ' + error);
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
