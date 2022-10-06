import axios, {AxiosResponse} from "axios";
import {LoginDTO, LoginResponse, RegisterDTO} from "./usermanagement/AuthModels";


function createHeaders() {
    return {
        headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
    }
}

export function apiRegister(user: RegisterDTO) {
    const url = `${process.env.REACT_APP_BACKEND_URL}/api/auth/register`;
    console.log(`post: ${url}: user=${user}`);
    return axios.post(url, user)
        .then(r => r.data);
}

export function apiLogin(user: LoginDTO) {
    const url = `${process.env.REACT_APP_BACKEND_URL}/api/auth/login`;
    console.log(`post: ${url}: user=${user.username}`);
    return axios.post(url, user)
        .then((response: AxiosResponse<LoginResponse>) => response.data)
}


export function apiGetUserInfo(){
    const url = `${process.env.REACT_APP_BACKEND_URL}/api/auth/userinfo`;
    console.log(`get: ${url}`);
    return axios.get(
        url,
        createHeaders()
    ).then((response: AxiosResponse) => response.data)
}
