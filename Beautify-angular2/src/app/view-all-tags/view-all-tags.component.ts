import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { TagService } from '../tag.service';
import { Tag } from '../tag';

@Component({
  selector: 'app-view-all-tags',
  templateUrl: './view-all-tags.component.html',
  styleUrls: ['./view-all-tags.component.css']
})
export class ViewAllTagsComponent implements OnInit 
{
	tags: Tag[];
	newTag: Tag;
	tagToDelete: Tag;
	displayTag: boolean = false;
	
	displayCreate: boolean = false;
	displayDelete: boolean = false;
	submitted: boolean;
	
	resultSuccess: boolean;
	resultError: boolean;
	message: string;
	
	cols: any[];
	
	constructor(private router: Router,
				private activatedRoute: ActivatedRoute,
				public sessionService: SessionService,
				private tagService: TagService,
				)
	{	
		this.newTag = new Tag();
		this.submitted = false;
		this.resultSuccess = false;
		this.resultError = false;
	}
	
	ngOnInit(): void {
		this.tagService.getTags().subscribe(
		response => {
			this.tags = response.tags;
		},
		error => {
			console.log('********** ViewAllTagsComponent.ts: ' + error);
		}
	  );
	  
	  this.cols = [
		{ field: 'tagId',header:'Tag ID' },
		{ field: 'name',header:'Tag name' },
	  ];
	  
	}
	showDialog() {
		this.displayCreate = true;
	}
	
	showDeleteDialog(tagToDelete: Tag) {
    this.displayDelete = true;
    this.tagToDelete = tagToDelete;
  }
		
	clear() {
		this.submitted = false;
		this.newTag = new Tag();
	}

	create(createTagForm: NgForm)
	{
		this.submitted = true;
		//if (createTagForm.valid)
		//{
			this.tagService.createTag(this.newTag).subscribe(
			response => {
				let newTagId = response.newTagId;
				this.resultSuccess = true;
				this.resultError = false;
				this.message = "New tag " + newTagId + " created successfully";
				this.tagService.getTags().subscribe(
					response => {
						this.tags = response.tags;
					},
					error => {
						console.log('********** ViewAllTagsComponent.ts: ' + error);
					}
				)
			},
			error => {
				this.resultError = true;
				this.resultSuccess = false;
				this.message = "An error has occurred while creating the new tag: " + error;
				console.log('********** CreateNewTagComponent.ts: ' + error);
			}
			);
		//}
		this.displayCreate = false;
	}
	
	delete(deleteTagForm: NgForm) {
    this.tagService.deleteTag(this.tagToDelete.tagId).subscribe(
      response => {
        this.router.navigate(["/view-all-tags"]);
		
		this.tagService.getTags().subscribe(
			response => {
				this.tags = response.tags;
			},
			error => {
				console.log('********** ViewAllTagsComponent.ts: ' + error);
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
