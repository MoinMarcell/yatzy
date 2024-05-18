import {JSX} from "react";
import "./HomePage.css";
import {faArrowRightToBracket, faX} from "@fortawesome/free-solid-svg-icons";
import CustomButton from "../components/CustomButton.tsx";
import Avatar from "../components/Avatar.tsx";
import {AppUser} from "../types/AppUser.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";

type HomePageProps = {
    appUser: AppUser,
    logout: () => void,
    setOpenMenu: (openMenu: boolean) => void,
    openMenu: boolean
}

export default function HomePage(props: Readonly<HomePageProps>): JSX.Element {
    const navigate = useNavigate();

    return (
        <div className="home-page-container">
            <div className="home-page-avatar">
                <h3>Welcome back, {props.appUser.username}!</h3>
                <Avatar onClick={() => props.setOpenMenu(!props.openMenu)} avatarUrl={props.appUser.avatarUrl}/>
                <div className={"home-page-avatar-menu " + (props.openMenu ? "display-flex" : "display-none")}>
                    <button className="home-page-avatar-menu-close-button" onClick={() => props.setOpenMenu(false)}>
                        <FontAwesomeIcon icon={faX}/></button>
                    <button className="home-page-avatar-menu-button" onClick={() => {
                        props.setOpenMenu(false);
                        navigate("/confirm/delete");
                    }}>Delete
                        Account
                    </button>
                </div>
            </div>
            <div className="home-page-buttons">
                <CustomButton
                    onClick={props.logout}
                    text="Logout"
                    icon={faArrowRightToBracket}
                    iconRotation={180}
                />
            </div>
        </div>
    )
}