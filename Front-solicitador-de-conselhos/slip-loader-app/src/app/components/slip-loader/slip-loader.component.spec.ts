import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlipLoaderComponent } from './slip-loader.component';

describe('SlipLoaderComponent', () => {
  let component: SlipLoaderComponent;
  let fixture: ComponentFixture<SlipLoaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SlipLoaderComponent]
    });
    fixture = TestBed.createComponent(SlipLoaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
