import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogEditPropuestaComponent } from './dialog-edit-propuesta.component';

describe('DialogEditPropuestaComponent', () => {
  let component: DialogEditPropuestaComponent;
  let fixture: ComponentFixture<DialogEditPropuestaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogEditPropuestaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogEditPropuestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
