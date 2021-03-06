import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule, MatCardModule, MatButtonModule, MatListModule, MatProgressBarModule, MatMenuModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutes } from './dashboard.routing';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MateriasComponent } from './materias/materias/materias.component';
import { PartidosComponent } from './partidos/partidos.component';
import { PropuestasComponent } from './propuestas/propuestas.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(DashboardRoutes),
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatListModule,
    MatProgressBarModule,
    MatMenuModule,
    FlexLayoutModule,
    MatTableModule,
    FormsModule,
    ReactiveFormsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ DashboardComponent, UsuariosComponent, MateriasComponent, PartidosComponent, PropuestasComponent ]
})

export class DashboardModule {}
