import { UsuarioResponse } from "./usuario-response";

export interface LoginResponse {
    token: string;
    user: UsuarioResponse;
}
