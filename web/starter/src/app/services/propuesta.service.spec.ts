import { TestBed, inject } from '@angular/core/testing';

import { PropuestaService } from './propuesta.service';

describe('PropuestaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PropuestaService]
    });
  });

  it('should be created', inject([PropuestaService], (service: PropuestaService) => {
    expect(service).toBeTruthy();
  }));
});
