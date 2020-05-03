import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { CategoryService } from '../category.service';
import { TagService } from '../tag.service';
import { ServiceProvider } from '../service-provider';
import { Tag } from '../tag';
import { Category } from '../category';
import { Service } from '../service';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-view-all-services',
  templateUrl: './view-all-services.component.html',
  styleUrls: ['./view-all-services.component.css']
})
export class ViewAllServicesComponent implements OnInit {

  services: Service[];
  newService: Service;
  serviceToView: Service;
  serviceToUpdate: Service;
  serviceToDelete: Service;
  serviceProvider: ServiceProvider;
  displayAdd: boolean = false;
  displayView: boolean = false;
  displayUpdate: boolean = false;
  displayDelete: boolean = false;
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

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private serviceService: ServiceService,
    private categoryService: CategoryService,
    private tagService: TagService) {

    this.submitted = false;
    this.newService = new Service();

    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit(): void {
    this.serviceProvider = this.sessionService.getCurrentServiceProvider();

    this.serviceService.getServices().subscribe(
      response => {
        this.services = response.services;
      },
      error => {
        console.log('********** ViewAllProductsComponent.ts: ' + error);
      }
    );

    this.categoryService.getServiceCategories().subscribe(
      response => {
        this.categories = response.categories;
      },
      error => {
        console.log('********** CreateNewProductComponent.ts: ' + error);
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

  showViewDialog(serviceToView: Service) {
    this.displayView = true;
    this.serviceToView = serviceToView;
  }

  showUpdateDialog(serviceToUpdate: Service) {
    this.displayUpdate = true;
    this.serviceToUpdate = serviceToUpdate;
  }

  showDeleteDialog(serviceToDelete: Service) {
    this.displayDelete = true;
    this.serviceToDelete = serviceToDelete;
  }


  clear() {
    this.submitted = false;
    this.newService = new Service();
  }

  create(addServiceForm: NgForm) {

    this.categoryId = this.selectedCategory.categoryId;

    let longTagIds: number[] = new Array();

    for (var i = 0; i < this.selectedTags.length; i++) {
      longTagIds.push(this.selectedTags[i].tagId);
    }

    this.submitted = true;

    // if (addProductForm.valid) {
    this.serviceService.createService(this.newService, this.categoryId, longTagIds).subscribe(
      response => {
        let newServiceId: number = response.serviceId;
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "New service " + newServiceId + " created successfully";
      },
      error => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = "An error has occurred while creating the new service: " + error;

        console.log('********** CreateNewServiceComponent.ts: ' + error);
      }
    );
    // }
    this.displayAdd = false;
  }

  update(updateServiceForm: NgForm) {

    this.categoryId = this.selectedCategory.categoryId;
    let longTagIds: number[] = new Array();

    for (var i = 0; i < this.selectUpdatedTags.length; i++) {
      longTagIds.push(this.selectUpdatedTags[i].tagId);
    }

    this.submitted = true;

    // if (updateProductForm.valid) {
    this.serviceService.updateService(this.serviceToUpdate, this.categoryId, longTagIds).subscribe(
      response => {
        this.resultSuccess = true;
        this.resultError = false;
        this.message = "Service updated successfully";
        console.log(this.message);
        this.displayUpdate = false;
      },
      error => {
        this.resultError = true;
        this.resultSuccess = false;
        this.message = "An error has occurred while updating the service: " + error;

        console.log('********** UpdateServiceComponent.ts: ' + error);
      }
    );
    // }
  }

  delete(deleteServiceForm: NgForm) {
    this.serviceService.deleteService(this.serviceToDelete.serviceId).subscribe(
      response => {
        this.router.navigate(["/view-all-services"]);
        this.displayDelete = false;
      },
      error => {
        console.log('********** DeleteServiceComponent.ts: ' + error);
      }
    );
  }
}
