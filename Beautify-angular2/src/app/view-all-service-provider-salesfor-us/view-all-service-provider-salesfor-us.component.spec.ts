import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllServiceProviderSalesforUsComponent } from './view-all-service-provider-salesfor-us.component';

describe('ViewAllServiceProviderSalesforUsComponent', () => {
  let component: ViewAllServiceProviderSalesforUsComponent;
  let fixture: ComponentFixture<ViewAllServiceProviderSalesforUsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllServiceProviderSalesforUsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllServiceProviderSalesforUsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
