import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllServicesComponent } from './view-all-services.component';

describe('ViewAllServicesComponent', () => {
  let component: ViewAllServicesComponent;
  let fixture: ComponentFixture<ViewAllServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
