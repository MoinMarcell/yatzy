import {JSX} from "react";
import "./CustomButton.css";
import {IconDefinition} from "@fortawesome/free-brands-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {RotateProp, SizeProp} from "@fortawesome/fontawesome-svg-core";

type CustomButtonProps = {
    type?: "submit" | "reset" | "button",
    text: string,
    icon?: IconDefinition,
    iconRotation?: RotateProp,
    iconSize?: SizeProp,
    onClick?: () => void,
}

export default function CustomButton(props: CustomButtonProps): JSX.Element {
    return (
        <button
            type={props.type}
            className="custom-button"
            onClick={props.onClick}
        >
            {
                props.icon &&
                <FontAwesomeIcon
                    icon={props.icon}
                    size={props.iconSize}
                    rotation={props.iconRotation}
                />
            }&nbsp;{props.text}
        </button>
    )
}