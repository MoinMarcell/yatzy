import axios from "axios";
import {JSX, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./ConfirmDeleteAccountPage.css";
import CustomButton from "../components/CustomButton.tsx";
import {faBan, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {AppUser} from "../types/AppUser.ts";
import {toast} from "react-toastify";

type ConfirmDeleteAccountPageProps = {
    appUser: AppUser,
    logout: () => void
    setOpenMenu: (openMenu: boolean) => void
}

export default function ConfirmDeleteAccountPage(props: Readonly<ConfirmDeleteAccountPageProps>): JSX.Element {
    const [isDisabled, setIsDisabled] = useState<boolean>(false);
    const navigate = useNavigate();

    function deleteAccount(): void {
        setIsDisabled(true);
        axios.delete("/api/users/" + props.appUser.id)
            .then(() => {
                toast.success("Account deleted. You will be redirected to the login page.", {
                    onClose: () => props.logout()
                });
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
                    disabled={isDisabled}
                />
                <CustomButton
                    onClick={() => navigate("/")}
                    text="Cancel"
                    icon={faBan}
                    disabled={isDisabled}
                />
            </div>
        </div>
    );
}