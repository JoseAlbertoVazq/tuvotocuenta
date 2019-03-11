import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Partido} from '../interfaces/partido-response';
import {PartidoContainer} from '../interfaces/partido-container';

@Injectable({
  providedIn: 'root'
})
export class PartidoService {

  constructor() { }
}
