import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashTopSectionComponent } from './dash-top-section.component';

describe('DashTopSectionComponent', () => {
  let component: DashTopSectionComponent;
  let fixture: ComponentFixture<DashTopSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashTopSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashTopSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
