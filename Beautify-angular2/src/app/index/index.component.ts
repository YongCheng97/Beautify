import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { StaffService } from '../staff.service';
import { Staff } from '../staff';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

	@Output() 
	childEvent = new EventEmitter();

  username: string;
  password: string;
  loginError: boolean;
  errorMessage: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    public staffService: StaffService) {
      this.loginError = false;
  }

  ngOnInit() {
	  this.sessionService.setUsername(null);
	  this.sessionService.setPassword(null);
	  this.sessionService.setIsLogin(false);
	  this.sessionService.setCurrentStaff(null);
  }

  login(): void {
    this.sessionService.setUsername(this.username);
    this.sessionService.setPassword(this.password);

    this.staffService.login(this.username, this.password).subscribe(
      response => {
        let staff: Staff = response.staff;

        if (staff != null) {
          this.sessionService.setIsLogin(true);
          this.sessionService.setCurrentStaff(staff);
          console.log(staff.name);
          sessionStorage.setItem('staff', JSON.stringify(staff));
          this.loginError = false;

          this.childEvent.emit();

          this.router.navigate(["/main-page"]);
        } else {
          this.loginError = true;
        }
      },
      error => {
        this.loginError = true;
        this.errorMessage = error;
      }
    );
  }
}