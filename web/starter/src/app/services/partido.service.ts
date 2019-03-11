import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Partido} from '../interfaces/partido-response';
import {PartidoContainer} from '../interfaces/partido-container';
import {PartidoCreateDto} from '../dto/partido-create-dto';

const partidoUrl = `${environment.apiUrl}/partidos`;

@Injectable({
  providedIn: 'root'
})
export class PartidoService {
  token = `?access_token=${this.authService.getToken()}`;
  master = `?access_token=${environment.masterKeyNoAcc}`;
  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllPartidos(): Observable<PartidoContainer> {
    return this.http.get<PartidoContainer>(`${partidoUrl}${this.token}`);
  }

  getOnePartido(id: string): Observable<Partido> {
    return this.http.get<Partido>(`${partidoUrl}/${id}${this.token}`)
  }

  createPartido(partidoCreateDto: PartidoCreateDto): Observable<Partido> {
    return this.http.post<Partido>(`${partidoUrl}${this.token}`,partidoCreateDto);
  }

  updatePartido(id: string, resource: PartidoCreateDto): Observable<Partido> {
    return this.http.put<Partido>(`${partidoUrl}/${id}${this.token}`, resource);
  }

  deletePartido(id: string): Observable<Partido> {
    return this.http.delete<Partido>(`${partidoUrl}/${id}${this.token}`);
  }
}
