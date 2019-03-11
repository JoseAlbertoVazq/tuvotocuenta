import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Propuesta} from '../interfaces/propuesta-response';
import {PropuestaContainer} from '../interfaces/propuesta-container';
import {PropuestaCreateDto} from '../dto/propuesta-create-dto';

const propuestaUrl = `${environment.apiUrl}/propuestas`;
@Injectable({
  providedIn: 'root'
})
export class PropuestaService {
  token = `?access_token=${this.authService.getToken()}`;
  master = `?access_token=${environment.masterKeyNoAcc}`;
  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllPropuestas(): Observable<PropuestaContainer> {
    return this.http.get<PropuestaContainer>(`${propuestaUrl}${this.token}`);
  }

  getOnePropuesta(id: string): Observable<Propuesta> {
    return this.http.get<Propuesta>(`${propuestaUrl}/${id}${this.token}`)
  }

  createPropuesta(propuestaCreateDto: PropuestaCreateDto): Observable<Propuesta> {
    return this.http.post<Propuesta>(`${propuestaUrl}${this.token}`, propuestaCreateDto);
  }

  updatePropuesta(id: string, resource: PropuestaCreateDto): Observable<Propuesta> {
    return this.http.put<Propuesta>(`${propuestaUrl}/${id}${this.token}`, resource);
  }

  deletePropuesta(id: string): Observable<Propuesta> {
    return this.http.delete<Propuesta>(`${propuestaUrl}/${id}${this.token}`);
  }

}
