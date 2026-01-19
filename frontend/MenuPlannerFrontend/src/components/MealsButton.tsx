import type {ButtonType} from "../types/ButtonType";

export default function MealsButton({type, text, onClick}: ButtonType) {
    return (
        <button
            type={type}
            onClick={onClick}
            className={"bg-primary text-white rounded-md m-2 p-2 shadow-lg hover:text-2xl hover:cursor-pointer"}
        >
            {text}
        </button>
    )
}
