import { Injectable } from '@angular/core';

export interface BadgeItem {
  type: string;
  value: string;
}

export interface ChildrenItems {
  state: string;
  name: string;
  type?: string;
}

export interface Menu {
  state: string;
  name: string;
  type: string;
  icon: string;
  badge?: BadgeItem[];
  children?: ChildrenItems[];
}

const MENUITEMS = [
  {
    state: '/',
    name: 'Login',
    type: 'link',
    icon: 'explore'
  },
  {
    state: '/dashboard',
    name: 'Usuarios',
    type: 'link',
    icon: 'assignment_ind'
  },
  {
    state: '/dashboard/materias',
    name: 'Materias',
    type: 'link',
    icon: 'swap_horiz'
  }, {
    state: '/dashboard/partidos',
    name: 'Partidos',
    type: 'link',
    icon: 'swap_horizontal_circle'
  },
  {
    state: '/dashboard/propuestas',
    name: 'Propuestas Electorales',
    type: 'link',
    icon: 'build'
  }
];

@Injectable()
export class MenuService {
  getAll(): Menu[] {
    return MENUITEMS;
  }

  add(menu) {
    MENUITEMS.push(menu);
  }
}
