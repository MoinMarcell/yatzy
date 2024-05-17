import {JSX} from "react";
import "./HomePage.css";
import {faArrowRightToBracket} from "@fortawesome/free-solid-svg-icons";
import CustomButton from "../components/CustomButton.tsx";

export default function HomePage(): JSX.Element {
    function logout(): void {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/logout", "_self")
    }

    return (
        <div className="home-page-container">
            <CustomButton
                onClick={logout}
                text="Logout"
                icon={faArrowRightToBracket}
                iconRotation={180}
            />
        </div>
    )
}