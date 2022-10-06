import {useNavigate} from "react-router-dom";
import {Box, Button, Grid, Typography} from "@mui/material";
import React from "react";
import {useAuth} from "../usermanagement/AuthProvider";
import LogoutIcon from '@mui/icons-material/Logout';
import {Toaster} from "react-hot-toast";


export default function Header() {
    const nav = useNavigate();
    const {logout} = useAuth();
    return (
        <Box display={'flex'} justifyContent={'center'} m={1} mt={1.5}>
            <Toaster
                position="top-right"
                reverseOrder={false}
            />
            <Grid container
                  border={0.1} borderColor={"lightgrey"} borderRadius={2}
                  mt={1} ml={1} mr={1} mb={2}
                  flexGrow={1} justifyContent={'center'} alignItems={'center'}
            >
                <Grid item xs={4}>
                </Grid>
                <Grid item xs={4}>
                    <Grid container justifyContent={'center'}>
                        <Button onClick={() => nav("/")}>
                            <Typography variant={"h3"}>{process.env.REACT_APP_APPLICATION_NAME}</Typography>
                        </Button>
                    </Grid>
                </Grid>
                <Grid item xs={4}>
                    <Grid container justifyContent={'end'} justifyItems={'end'} alignItems={'center'}>
                        <Grid item>
                            {localStorage.getItem('jwt') ?
                                <Grid container direction={'row'} alignItems={'center'} justifyItems={'end'}
                                      wrap={'nowrap'}>
                                    <Grid item mr={2}>
                                        <LogoutIcon onClick={logout} color={'primary'}/>
                                    </Grid>
                                </Grid>
                                :
                                <Grid container direction={'column'}>
                                    <Grid item>
                                        <Button color="inherit" size={"small"} onClick={() => nav("/login")}>
                                            Login
                                        </Button>
                                    </Grid>
                                    <Grid item>
                                        <Button color="inherit" size={"small"} onClick={() => nav("/register")}>
                                            Register
                                        </Button>
                                    </Grid>
                                </Grid>
                            }
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
    )
}
