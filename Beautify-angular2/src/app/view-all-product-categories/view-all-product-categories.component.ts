import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { CategoryService } from '../category.service';
import { Category } from '../category';

@Component({
  selector: 'app-view-all-product-categories',
  templateUrl: './view-all-product-categories.component.html',
  styleUrls: ['./view-all-product-categories.component.css']
})
export class ViewAllProductCategoriesComponent implements OnInit {

	productCategories: Category[];
	
	cols: any[];

	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private categoryService: CategoryService,
				) 
				{ 
				
				}
	
	ngOnInit(): void {
		this.categoryService.getProductCategories().subscribe(
		response => {
			this.productCategories = response.categories;
		},
		error => {
			console.log('********** ViewAllProductCategoriesComponentComponent.ts: ' + error);
		}
		);
		
		this.cols = [
		{ field: 'categoryId', header:'Category Id'},
		{ field: 'name', header:'Category name'},
		{ field: 'description', header:'Category description'},
		{ field: 'type', header:'Category type'},
		{ feild: '',header:'Delete Category'}
		];
		
	}

}
