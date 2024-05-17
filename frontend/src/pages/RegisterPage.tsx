import {FormEvent, JSX, useCallback, useEffect, useState} from "react";
import axios from "axios";
import {AppUser} from "../types/AppUser.ts";
import {Navigate, useNavigate} from "react-router-dom";
import "./RegisterPage.css";
import {faCheck, faSpinner, faX} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

type RegisterPageProps = {
    appUser: AppUser | null | undefined,
    setAppUser: (appUser: AppUser) => void
}

export default function RegisterPage({appUser, setAppUser}: RegisterPageProps): JSX.Element {
    const [username, setUsername] = useState<string>("");
    const [icon, setIcon] = useState<"" | "loading" | "success" | "error">("");
    const [isError, setIsError] = useState<boolean>(username.length < 3 || username.length > 20 || !username.match(/^[a-zA-Z0-9]+$/));
    const navigate = useNavigate();

    const checkUsername = useCallback((): void => {
        axios.get(`/api/users/check-username/${username}`)
            .then((res) => {
                const isAvailable: boolean = res.data;
                if (isAvailable) {
                    setIcon("success");
                    setIsError(false);
                } else {
                    setIcon("error");
                    setIsError(true);
                }
            })
            .catch((err) => {
                console.log(err);
            });
    }, [username]);

    useEffect(() => {
        let timeoutId: number | undefined;
        if (username.length > 2 && username.length < 21) {
            setIcon("loading");
            setIsError(true);
            timeoutId = setTimeout(() => {
                checkUsername();
            }, 1000);
        } else if (username.length === 0) {
            setIcon("");
            setIsError(true);
        } else if (username.length > 20 || username.length < 3) {
            setIcon("error");
            setIsError(true);
        }

        return () => {
            clearTimeout(timeoutId);
        }
    }, [checkUsername, username]);

    if (!appUser) {
        return <Navigate to="/login"/>
    }

    if (appUser.username) {
        return <Navigate to="/"/>
    }

    const handleSubmit = (e: FormEvent<HTMLFormElement>): void => {
        e.preventDefault();
        axios.put(`/api/users/${appUser.id}/${username}`)
            .then((res) => {
                setAppUser(res.data);
                navigate("/");
            })
            .catch((err) => {
                console.log(err);
            });
    }

    let fontAwesomeIcon: JSX.Element;
    switch (icon) {
        case "loading":
            fontAwesomeIcon = <FontAwesomeIcon icon={faSpinner} className="input-icon" spinPulse
                                               size="xl" color={"white"}/>;
            break;
        case "success":
            fontAwesomeIcon = <FontAwesomeIcon icon={faCheck} className="input-icon-check" size="xl"
                                               style={{color: "#63E6BE",}}/>;
            break;
        case "error":
            fontAwesomeIcon = <FontAwesomeIcon icon={faX} size="xl" className="input-icon-check"
                                               style={{color: "#d60000",}}/>;
            break;
        default:
            fontAwesomeIcon = <></>;
            break;
    }

    return (
        <form onSubmit={handleSubmit} className="register-page-form">
            <div className="register-page-container">
                <div className="register-page-infobox-container">
                    <h3>Welcome!</h3>
                    <p>
                        It looks like you're new here. Please choose a username to create your account.
                    </p>
                    <p>
                        Your Username should be between 3 and 20 characters long and only contain letters and numbers.
                    </p>
                </div>
                <div className="register-page-input-container">
                    {fontAwesomeIcon}
                    <input
                        className={username.length > 0 ? "register-page-input-padding-left-50" : "register-page-input-padding-left-10"}
                        placeholder="Username"
                        id="username"
                        name="username"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="register-page-button" disabled={isError}>
                    Complete Registration
                </button>
            </div>
        </form>
    )
}