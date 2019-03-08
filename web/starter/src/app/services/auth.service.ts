import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginDto } from '../dto/login-dto';
import { environment } from 'src/environments/environment.prod';
import { Observable } from 'rxjs';
import { LoginResponse } from '../interfaces/login-response';

const authUrl = `${environment.apiUrl}`;

const requestOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(loginDto: LoginDto): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${authUrl}/auth`, loginDto, requestOptions);
  }

  setLoginData(loginResponse: LoginResponse) {
    localStorage.setItem('token', loginResponse.token);
    localStorage.setItem('username', loginResponse.username);
    localStorage.setItem('email', loginResponse.email);
    localStorage.setItem('rol', loginResponse.rol);
  }

}
