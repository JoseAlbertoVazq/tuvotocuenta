import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogEditPartidoComponent } from './dialog-edit-partido.component';

describe('DialogEditPartidoComponent', () => {
  let component: DialogEditPartidoComponent;
  let fixture: ComponentFixture<DialogEditPartidoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogEditPartidoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogEditPartidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
