import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import {UsuariosComponent} from './usuarios/usuarios.component';

export const DashboardRoutes: Routes = [{
  path: '',
  component: UsuariosComponent
}];
