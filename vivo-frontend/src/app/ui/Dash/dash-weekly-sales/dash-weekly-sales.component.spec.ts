import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashWeeklySalesComponent } from './dash-weekly-sales.component';

describe('DashWeeklySalesComponent', () => {
  let component: DashWeeklySalesComponent;
  let fixture: ComponentFixture<DashWeeklySalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashWeeklySalesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashWeeklySalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
