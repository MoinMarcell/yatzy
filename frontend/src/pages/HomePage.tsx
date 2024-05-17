import {JSX, useState} from "react";
import "./HomePage.css";
import {faArrowRightToBracket, faX} from "@fortawesome/free-solid-svg-icons";
import CustomButton from "../components/CustomButton.tsx";
import Avatar from "../components/Avatar.tsx";
import {AppUser} from "../types/AppUser.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

type HomePageProps = {
    appUser: AppUser
}

export default function HomePage(props: HomePageProps): JSX.Element {
    const [openMenu, setOpenMenu] = useState<boolean>(false);

    function logout(): void {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/logout", "_self")
    }

    return (
        <div className="home-page-container">
            <div className="home-page-avatar">
                <h3>Welcome back, {props.appUser.username}!</h3>
                <Avatar onClick={() => setOpenMenu(!openMenu)} avatarUrl={props.appUser.avatarUrl}/>
                <div className={"home-page-avatar-menu " + (openMenu ? "display-flex" : "display-none")}>
                    <button className="home-page-avatar-menu-close-button" onClick={() => setOpenMenu(false)}>
                        <FontAwesomeIcon icon={faX}/></button>
                    <button className="home-page-avatar-menu-button" disabled>Delete Account</button>
                </div>
            </div>
            <div className="home-page-buttons">
                <CustomButton
                    onClick={logout}
                    text="Logout"
                    icon={faArrowRightToBracket}
                    iconRotation={180}
                />
            </div>
        </div>
    )
}