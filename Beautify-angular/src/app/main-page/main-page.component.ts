import { Component, OnInit } from '@angular/core';

import { SessionService } from '../session.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

export class MainPageComponent implements OnInit {

  constructor(public sesionService: SessionService) { }

  ngOnInit() {
  }

}
