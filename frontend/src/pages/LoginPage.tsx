import {JSX} from "react";
import {AppUser} from "../types/AppUser.ts";
import {Navigate} from "react-router-dom";
import "./LoginPage.css";
import {faArrowRightToBracket} from "@fortawesome/free-solid-svg-icons";
import CustomButton from "../components/CustomButton.tsx";

type LoginPageProps = {
    appUser: AppUser | null | undefined
}

export default function LoginPage({appUser}: Readonly<LoginPageProps>): JSX.Element {
    if (appUser) {
        return <Navigate to="/"/>
    }

    function login(): void {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/oauth2/authorization/google", "_self")
    }

    return (
        <div className="login-page">
            <CustomButton
                onClick={login}
                text="Login with Google"
                icon={faArrowRightToBracket}
            />
        </div>
    )
}