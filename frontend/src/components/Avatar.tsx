import {JSX} from "react";
import './Avatar.css';

type AvatarProps = {
    avatarUrl: string,
    alt?: string,
    onClick?: () => void,
    onMouseOver?: () => void,
}

export default function Avatar(props: Readonly<AvatarProps>): JSX.Element {
    return (
        <img
            src={props.avatarUrl}
            alt={props.alt ? props.alt : "avatar"}
            className="avatar"
            onClick={props.onClick}
            onMouseOver={props.onMouseOver}
        />
    );
}