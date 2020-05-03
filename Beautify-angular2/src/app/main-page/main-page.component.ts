import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { StaffService } from '../staff.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

export class MainPageComponent implements OnInit {

  staff: Staff;
 

  constructor(public sessionService: SessionService,
    private staffService: StaffService, 
    private router: Router, 
    private activatedRouter: ActivatedRoute
    )
  {

  }

  ngOnInit() {
    this.staff = this.sessionService.getCurrentStaff();
  }

  

}
