import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "./usermanagement/LoginPage";
import RegisterPage from "./usermanagement/RegisterPage";
import AuthProvider from "./usermanagement/AuthProvider";
import Header from "./pages/Header";
import Home from "./pages/Home";
import Statistics from "./pages/Statistics";

export default function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <Header/>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/register" element={<RegisterPage/>}/>
                    <Route path="/statistics" element={<Statistics/>}/>
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    );
}
