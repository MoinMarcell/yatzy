import {JSX} from "react";
import './Avatar.css';

type AvatarProps = {
    avatarUrl: string,
    alt?: string,
    onClick?: () => void,
}

export default function Avatar(props: Readonly<AvatarProps>): JSX.Element {
    return (
        <button
            className="avatar-button"
            onClick={props.onClick}
        >
            <img
                src={props.avatarUrl}
                alt={props.alt ? props.alt : "avatar"}
                className="avatar"
            />
        </button>
    );
}