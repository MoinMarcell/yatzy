import {Navigate, Outlet} from "react-router-dom";
import {JSX} from "react";
import {AppUser} from "../types/AppUser.ts";

type ProtectedRoutesProps = {
    appUser: AppUser | null | undefined
}

export default function ProtectedRoutes({appUser}: Readonly<ProtectedRoutesProps>): JSX.Element {
    if (appUser === undefined) {
        return <h1>Loading...</h1>
    } else if (appUser === null) {
        return <Navigate to="/login"/>
    } else {
        return <Outlet/>
    }
}
