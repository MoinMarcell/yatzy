import {JSX} from "react";

export default function HomePage(): JSX.Element {
    function logout(): void {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/logout", "_self")
    }

    return (
        <>
            <button onClick={logout}>Logout</button>
            <h1>Home</h1>
        </>
    )
}