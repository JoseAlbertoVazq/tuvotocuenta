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
  constructor(private http: HttpClient, private authService: AuthService) { }
  getAllMaterias(): Observable<MateriaContainer> {
    return this.http.get<MateriaContainer>(`${materiaUrl}${this.token}`);
  }

  createMateria(categoryCreateDto: MateriaCreateDto): Observable<Materia> {
    return this.http.post<Materia>(`${materiaUrl}${this.token}`, MateriaCreateDto);
  }

  updateMateria(id: string, resource: MateriaCreateDto): Observable<Materia> {
    return this.http.put<Materia>(`${materiaUrl}/${id}${this.token}`, resource);
  }

  deleteMateria(id: number): Observable<Materia> {
    return this.http.delete<Materia>(`${materiaUrl}/${id}${this.token}`);
  }

}
