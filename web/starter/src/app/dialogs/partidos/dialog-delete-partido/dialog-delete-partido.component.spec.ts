import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDeletePartidoComponent } from './dialog-delete-partido.component';

describe('DialogDeletePartidoComponent', () => {
  let component: DialogDeletePartidoComponent;
  let fixture: ComponentFixture<DialogDeletePartidoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogDeletePartidoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDeletePartidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
