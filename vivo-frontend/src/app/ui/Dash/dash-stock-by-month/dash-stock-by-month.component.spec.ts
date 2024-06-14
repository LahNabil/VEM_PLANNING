import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashStockByMonthComponent } from './dash-stock-by-month.component';

describe('DashStockByMonthComponent', () => {
  let component: DashStockByMonthComponent;
  let fixture: ComponentFixture<DashStockByMonthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashStockByMonthComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashStockByMonthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
