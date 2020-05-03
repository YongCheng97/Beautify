import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllBookingSalesForUsComponent } from './view-all-booking-sales-for-us.component';

describe('ViewAllBookingSalesForUsComponent', () => {
  let component: ViewAllBookingSalesForUsComponent;
  let fixture: ComponentFixture<ViewAllBookingSalesForUsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllBookingSalesForUsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllBookingSalesForUsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
