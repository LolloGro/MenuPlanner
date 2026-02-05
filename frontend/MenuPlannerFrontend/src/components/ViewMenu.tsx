import type {ReadMenu} from "../types/Menu";
import DefaultButton from "./DefaultButton";
import {DAYS} from "../types/Weekday";

export default function ViewMenu({menu, onClose}:{menu:ReadMenu, onClose: () => void}){

    return(
        <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
            <div className={"flex flex-col justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-4"}>
                <div className={"flex justify-end"}>
                    <DefaultButton type={"button"} text={"Close"} onClick={onClose}/>
                </div>
                <div className={"overflow-auto"}>
                    <div>
                        <p className={"text-xl font-bold"}>{menu.menuName}</p>
                        {menu.meals.map((meal,index) => (
                            <ul>
                                <label className={"font-bold"} key={index}>{DAYS[index]}</label>
                                <li key={meal.id}>{meal.mealName}</li>
                            </ul>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}
