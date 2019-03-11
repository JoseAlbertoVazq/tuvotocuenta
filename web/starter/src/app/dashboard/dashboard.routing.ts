import { Routes } from '@angular/router';
import {MateriasComponent} from './materias/materias/materias.component'
import {UsuariosComponent} from './usuarios/usuarios.component';
import { PartidosComponent } from './partidos/partidos.component';
import { PropuestasComponent } from './propuestas/propuestas.component';

export const DashboardRoutes: Routes = [{
  path: '',
  component: UsuariosComponent
},
{
  path:'materias',
  component: MateriasComponent
},
{
  path:'partidos',
  component:PartidosComponent
},
{
  path:'propuestas',
  component:PropuestasComponent
}
];
