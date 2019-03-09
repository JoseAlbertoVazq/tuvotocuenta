import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginDto } from '../dto/login-dto';
import { environment } from 'src/environments/environment.prod';
import { Observable } from 'rxjs';
import { LoginResponse } from '../interfaces/login-response';

const authUrl = `${environment.apiUrl}`;



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  request(email: String, password: String) {
    let emailPass: String;
    emailPass = btoa(email + ':' + password);
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Basic ${emailPass}`,
        'Access-Control-Allow-Origin': '*'
      })
    };

    return requestOptions;
  }
  login(loginDto: LoginDto): Observable<LoginResponse> {
    const requestOptions = this.request(loginDto.email, loginDto.password);
    return this.http.post<LoginResponse>(`${authUrl}/auth`, environment.masterKey, requestOptions);
  }

  setLoginData(loginResponse: LoginResponse) {
    localStorage.setItem('token', loginResponse.token);
    localStorage.setItem('username', loginResponse.username);
    localStorage.setItem('email', loginResponse.email);
    localStorage.setItem('rol', loginResponse.rol);
    localStorage.setItem('city', loginResponse.city);
  }

  getToken(): string {
    return localStorage.getItem('token');
  }
  isAdmin() {
    return localStorage.getItem('role') === 'admin';
  }

}
