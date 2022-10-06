import {Box, Typography} from "@mui/material";
import {useEffect, useState} from "react";
import {apiGetUserInfo} from "../apiService";
import {useAuth} from "../usermanagement/AuthProvider";

interface UserInfo{
    username: string,
    id: string,
    roles: string[]
}

export default function Home() {
    const [userInfo, setUserInfo] = useState<UserInfo>();
    const {defaultApiResponseChecks} = useAuth();

    useEffect(()=>{
        apiGetUserInfo()
            .then(setUserInfo)
            .catch(err => {
                defaultApiResponseChecks(err);
            });
    }, [defaultApiResponseChecks])

    useEffect(()=>{
        console.log(userInfo)
    }, [userInfo])

    return (
        <Box>
            <Typography>Home  {process.env.REACT_APP_BACKEND_URL}</Typography>
            { localStorage.getItem('jwt') ?
                <>
                    <Typography>logged in</Typography>
                    { userInfo &&
                        <>
                            <Typography>Username: {userInfo?.username}</Typography>
                            <Typography>Id: {userInfo?.id}</Typography>
                            <Typography>roles: {userInfo?.roles}</Typography>
                        </>
                    }
                </>
                :
                <Typography>not logged in</Typography>
            }
        </Box>
    )
}
