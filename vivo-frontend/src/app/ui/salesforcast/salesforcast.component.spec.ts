import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesforcastComponent } from './salesforcast.component';

describe('SalesforcastComponent', () => {
  let component: SalesforcastComponent;
  let fixture: ComponentFixture<SalesforcastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SalesforcastComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SalesforcastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
