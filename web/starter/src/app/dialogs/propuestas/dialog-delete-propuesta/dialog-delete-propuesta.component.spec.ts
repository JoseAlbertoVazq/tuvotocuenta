import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDeletePropuestaComponent } from './dialog-delete-propuesta.component';

describe('DialogDeletePropuestaComponent', () => {
  let component: DialogDeletePropuestaComponent;
  let fixture: ComponentFixture<DialogDeletePropuestaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogDeletePropuestaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDeletePropuestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
