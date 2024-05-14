import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacStockComponent } from './bac-stock.component';

describe('BacStockComponent', () => {
  let component: BacStockComponent;
  let fixture: ComponentFixture<BacStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BacStockComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
