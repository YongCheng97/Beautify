import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllServiceProvidersComponent } from './view-all-service-providers.component';

describe('ViewAllServiceProvidersComponent', () => {
  let component: ViewAllServiceProvidersComponent;
  let fixture: ComponentFixture<ViewAllServiceProvidersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllServiceProvidersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllServiceProvidersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
