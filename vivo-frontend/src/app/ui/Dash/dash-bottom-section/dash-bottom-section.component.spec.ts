import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashBottomSectionComponent } from './dash-bottom-section.component';

describe('DashBottomSectionComponent', () => {
  let component: DashBottomSectionComponent;
  let fixture: ComponentFixture<DashBottomSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashBottomSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashBottomSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
