import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { UsuarioResponse } from '../interfaces/usuario-response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserCreateDto } from '../dto/user-create-dto';
import { UsuariosResponse } from '../interfaces/usuarios-response';
const usuarioUrl = `${environment.apiUrl}/users`;
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllUsuarios(): Observable<UsuariosResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`,
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        'Access-Control-Allow-Credentials': 'true'
      })
    };

    return this.http.get<UsuariosResponse>(`${usuarioUrl}`, requestOptions);
  }

  getOneUsuario(_id: String): Observable<UsuarioResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    };

    return this.http.get<UsuarioResponse>(`${usuarioUrl}/${_id}`, requestOptions);
  }

  createUsuario(usuarioCreateDto: UserCreateDto): Observable<UsuarioResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    };

    return this.http.post<UsuarioResponse>(`${usuarioUrl}`, usuarioCreateDto, requestOptions);
  }

  updateUsuario(usuario: UsuarioResponse): Observable<UsuarioResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    };
    return this.http.put<UsuarioResponse>(`${usuarioUrl}/${usuario._id}`, usuario, requestOptions);
  }

  deleteUsuario(id: String): Observable<UsuarioResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<UsuarioResponse>(`${usuarioUrl}/${id}`, requestOptions);
  }
}
