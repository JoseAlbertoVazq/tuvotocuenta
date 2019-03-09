import { UsuarioResponse } from './usuario-response';

export interface UsuariosResponse {
    count: number;
    rows: UsuarioResponse[];
}
