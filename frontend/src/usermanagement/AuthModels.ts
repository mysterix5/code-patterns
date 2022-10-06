import {AxiosError} from "axios";

export interface AuthInterface {
    username : string,
    roles : string[],
    logout: () => void,
    login: (token: string) => void,
    defaultApiResponseChecks: (err: Error | AxiosError) => void
}

export interface LoginResponse {
    token: string;
}

export interface LoginDTO {
    username: string,
    password: string
}

export interface RegisterDTO {
    username: string,
    password: string,
    passwordRepeat: string
}
