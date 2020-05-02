import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllBookingSalesRecordComponent } from './view-all-booking-sales-record.component';

describe('ViewAllBookingSalesRecordComponent', () => {
  let component: ViewAllBookingSalesRecordComponent;
  let fixture: ComponentFixture<ViewAllBookingSalesRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllBookingSalesRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllBookingSalesRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
