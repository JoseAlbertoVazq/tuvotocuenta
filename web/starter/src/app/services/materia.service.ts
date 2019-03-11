import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Materia } from '../interfaces/materias-response';
import { MateriaContainer } from '../interfaces/materia-container';
import {MateriaCreateDto} from '../dto/materia-create-dto';
const materiaUrl = `${environment.apiUrl}/materias`;

@Injectable({
  providedIn: 'root'
})
export class MateriaService {
  token = `?access_token=${this.authService.getToken()}`;
  master = `?access_token=${environment.masterKeyNoAcc}`;
  constructor(private http: HttpClient, private authService: AuthService) { }
  getAllMaterias(): Observable<MateriaContainer> {
    return this.http.get<MateriaContainer>(`${materiaUrl}${this.master}`);
  }

  createMateria(materiaCreateDto: MateriaCreateDto): Observable<Materia> {
    return this.http.post<Materia>(`${materiaUrl}${this.token}`,materiaCreateDto);
  }

  updateMateria(id: string, resource: MateriaCreateDto): Observable<Materia> {
    return this.http.put<Materia>(`${materiaUrl}/${id}${this.token}`, resource);
  }

  deleteMateria(id: string): Observable<Materia> {
    return this.http.delete<Materia>(`${materiaUrl}/${id}${this.token}`);
  }

}
