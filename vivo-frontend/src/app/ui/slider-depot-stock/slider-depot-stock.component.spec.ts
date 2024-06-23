import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SliderDepotStockComponent } from './slider-depot-stock.component';

describe('SliderDepotStockComponent', () => {
  let component: SliderDepotStockComponent;
  let fixture: ComponentFixture<SliderDepotStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SliderDepotStockComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SliderDepotStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
