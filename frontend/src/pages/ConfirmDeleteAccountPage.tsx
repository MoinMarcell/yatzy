import axios from "axios";
import {JSX} from "react";
import {useNavigate} from "react-router-dom";
import "./ConfirmDeleteAccountPage.css";
import CustomButton from "../components/CustomButton.tsx";
import {faBan, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {AppUser} from "../types/AppUser.ts";

type ConfirmDeleteAccountPageProps = {
    appUser: AppUser,
    logout: () => void
    setOpenMenu: (openMenu: boolean) => void
}

export default function ConfirmDeleteAccountPage(props: ConfirmDeleteAccountPageProps): JSX.Element {
    const navigate = useNavigate();

    function deleteAccount(): void {
        axios.delete("/api/users/" + props.appUser.id)
            .then(() => {
                props.logout();
            })
            .finally(() => props.setOpenMenu(false))
    }

    return (
        <div className="confirm-delete-account-page">
            <div className="confirm-delete-account-page-container">
                <h2>Are you sure you want to delete your account?</h2>
                <CustomButton
                    onClick={deleteAccount}
                    text="Delete Account"
                    icon={faTrashCan}
                />
                <CustomButton
                    onClick={() => navigate("/")}
                    text="Cancel"
                    icon={faBan}
                />
            </div>
        </div>
    );
}