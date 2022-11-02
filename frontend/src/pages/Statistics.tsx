import {useEffect, useState} from "react";
import {apiGetNoRegisteredUsers} from "../apiService";
import {Box, Typography} from "@mui/material";
import {useAuth} from "../usermanagement/AuthProvider";

export default function Statistics(){
    const [regUsers, setRegUsers] = useState(0);

    const {defaultApiResponseChecks} = useAuth();

    useEffect(()=>{
        apiGetNoRegisteredUsers()
                    .then(setRegUsers)
                    .catch(err => {
                        defaultApiResponseChecks(err);
                    });
    }, [])
    return (
    <>
        <Typography>number of registered users: {regUsers}</Typography>
    </>
    )
}