import {Route, Routes, useNavigate} from "react-router-dom";
import LoginPage from "./pages/LoginPage.tsx";
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";
import {JSX, useCallback, useEffect, useState} from "react";
import {AppUser} from "./types/AppUser.ts";
import axios from "axios";
import RegisterPage from "./pages/RegisterPage.tsx";
import HomePage from "./pages/HomePage.tsx";
import ConfirmDeleteAccountPage from "./pages/ConfirmDeleteAccountPage.tsx";

export default function App(): JSX.Element {
    const [appUser, setAppUser] = useState<AppUser | null | undefined>(undefined);
    const [openMenu, setOpenMenu] = useState<boolean>(false);
    const navigate = useNavigate();

    const fetchMe = useCallback(() => {
        axios.get("/api/auth/me")
            .then((response) => {
                setAppUser(response.data)
                if (response.data.username === null) {
                    navigate("/register")
                }
            })
            .catch((error) => {
                console.log(error);
                setAppUser(null);
            })
    }, [navigate])

    useEffect(() => {
        fetchMe()
    }, [fetchMe])

    function logout(): void {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/logout", "_self")
    }

    return (
        <Routes>
            <Route path="/login" element={<LoginPage appUser={appUser}/>}/>
            <Route element={<ProtectedRoutes appUser={appUser}/>}>
                <Route path="/" element={
                    //@ts-expect-error already checked in ProtectedRoutes
                    <HomePage logout={logout} setOpenMenu={setOpenMenu} openMenu={openMenu} appUser={appUser}/>
                }/>
                <Route path="/register" element={<RegisterPage appUser={appUser} setAppUser={setAppUser}/>}/>
                <Route path="/confirm/delete" element={
                    //@ts-expect-error already checked in ProtectedRoutes
                    <ConfirmDeleteAccountPage logout={logout} setOpenMenu={setOpenMenu} appUser={appUser}/>
                }/>
            </Route>
        </Routes>
    )
}
