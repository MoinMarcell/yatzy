import {JSX} from "react";
import {AppUser} from "../types/AppUser.ts";
import {Navigate} from "react-router-dom";

type LoginPageProps = {
    appUser: AppUser | null | undefined
}

export default function LoginPage({appUser}: LoginPageProps): JSX.Element {
    if (appUser) {
        return <Navigate to="/"/>
    }

    function login(): void {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/oauth2/authorization/google", "_self")
    }

    return (
        <button onClick={login}>
            Login with Google
        </button>
    )
}