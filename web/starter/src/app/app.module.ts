import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { LoadingBarRouterModule } from '@ngx-loading-bar/router';

import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';

import { AgmCoreModule } from '@agm/core';
import {MatInputModule} from '@angular/material/input';

import {MatTableModule} from '@angular/material/table';

import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatChipsModule} from '@angular/material/chips';
import {MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material/dialog';
import {
  MatSidenavModule,
  MatCardModule,
  MatMenuModule,
  MatCheckboxModule,
  MatIconModule,
  MatButtonModule,
  MatToolbarModule,
  MatTabsModule,
  MatListModule,
  MatSlideToggleModule,
  MatSelectModule,
  MatProgressBarModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import {BidiModule} from '@angular/cdk/bidi';

import {
  MenuComponent,
  HeaderComponent,
  SidebarComponent,
  NotificationComponent,
  OptionsComponent,
  AdminLayoutComponent,
  AuthLayoutComponent,
  AccordionAnchorDirective,
  AccordionLinkDirective,
  AccordionDirective} from './core';

import { AppRoutes } from './app.routing';
import { AppComponent } from './app.component';
import { DialogDeleteUsuarioComponent } from './dialogs/usuarios/dialog-delete-usuario/dialog-delete-usuario.component';
import { DialogNewMateriaComponent } from './dialogs/materias/dialog-new-materia/dialog-new-materia.component';
import { DialogEditMateriaComponent } from './dialogs/materias/dialog-edit-materia/dialog-edit-materia.component';
import { DialogDeleteMateriaComponent } from './dialogs/materias/dialog-delete-materia/dialog-delete-materia.component';
import { DialogNewPartidoComponent } from './dialogs/partidos/dialog-new-partido/dialog-new-partido.component';
import { DialogEditPartidoComponent } from './dialogs/partidos/dialog-edit-partido/dialog-edit-partido.component';
import { DialogDeletePartidoComponent } from './dialogs/partidos/dialog-delete-partido/dialog-delete-partido.component';
import { DialogNewPropuestaComponent } from './dialogs/propuestas/dialog-new-propuesta/dialog-new-propuesta.component';
import { DialogEditPropuestaComponent } from './dialogs/propuestas/dialog-edit-propuesta/dialog-edit-propuesta.component';
import { DialogDeletePropuestaComponent } from './dialogs/propuestas/dialog-delete-propuesta/dialog-delete-propuesta.component';

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
  wheelSpeed: 2,
  wheelPropagation: true,
  minScrollbarLength: 20
};

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidebarComponent,
    NotificationComponent,
    OptionsComponent,
    MenuComponent,
    AdminLayoutComponent,
    AuthLayoutComponent,
    AccordionAnchorDirective,
    AccordionLinkDirective,
    AccordionDirective,
    DialogDeleteUsuarioComponent,
    DialogNewMateriaComponent,
    DialogEditMateriaComponent,
    DialogDeleteMateriaComponent,
    DialogNewPartidoComponent,
    DialogEditPartidoComponent,
    DialogDeletePartidoComponent,
    DialogNewPropuestaComponent,
    DialogEditPropuestaComponent,
    DialogDeletePropuestaComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(AppRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    LoadingBarRouterModule,
    MatSidenavModule,
    MatCardModule,
    MatMenuModule,
    MatCheckboxModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatChipsModule,
    MatToolbarModule,
    MatTabsModule,
    MatListModule,
    MatSnackBarModule,
    MatSlideToggleModule,
    MatTableModule,
    MatDialogModule,
    MatSelectModule,
    MatProgressBarModule,
    FlexLayoutModule,
    BidiModule,
    AgmCoreModule.forRoot({apiKey: 'YOURAPIKEY'}),
    PerfectScrollbarModule
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    },
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: true}}
  ],
  bootstrap: [AppComponent],
  entryComponents:[
    DialogDeleteUsuarioComponent,
    DialogNewMateriaComponent,
    DialogEditMateriaComponent,
    DialogDeleteMateriaComponent,
    DialogNewPartidoComponent,
    DialogEditPartidoComponent,
    DialogDeletePartidoComponent
  ]
})
export class AppModule { }
