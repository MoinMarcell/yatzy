import {FormEvent, JSX, useState} from "react";
import axios from "axios";
import {AppUser} from "../types/AppUser.ts";
import {Navigate, useNavigate} from "react-router-dom";

type RegisterPageProps = {
    appUser: AppUser | null | undefined,
    setAppUser: (appUser: AppUser) => void
}

export default function RegisterPage({appUser, setAppUser}: RegisterPageProps): JSX.Element {
    const [username, setUsername] = useState<string>("");
    const navigate = useNavigate();

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

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
            <button>Complete Registration</button>
        </form>
    )
}