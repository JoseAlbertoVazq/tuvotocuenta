import { Routes } from '@angular/router';
import {MateriasComponent} from './materias/materias/materias.component'
import { DashboardComponent } from './dashboard.component';
import {UsuariosComponent} from './usuarios/usuarios.component';

export const DashboardRoutes: Routes = [{
  path: '',
  component: UsuariosComponent
},{
  path:'/materias',
  component: MateriasComponent
}];
