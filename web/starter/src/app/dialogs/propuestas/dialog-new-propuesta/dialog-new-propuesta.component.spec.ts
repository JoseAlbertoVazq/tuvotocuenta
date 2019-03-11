import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogNewPropuestaComponent } from './dialog-new-propuesta.component';

describe('DialogNewPropuestaComponent', () => {
  let component: DialogNewPropuestaComponent;
  let fixture: ComponentFixture<DialogNewPropuestaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogNewPropuestaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogNewPropuestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
