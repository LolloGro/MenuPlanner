import type {ButtonType} from "../types/ButtonType";

export default function PageButton({type, text, onClick}: ButtonType) {
    return (
        <button
            type={type}
            onClick={onClick}
            className={"bg-primary text-white rounded-md m-2 h-16 w-60 shadow-lg hover:bg-link-focus hover:cursor-pointer"}
        >
            {text}
        </button>
    )
}
