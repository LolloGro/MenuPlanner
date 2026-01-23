import {useState} from "react";
import SelectMeal from "../components/SelectMeal";
import type {Menu} from "../types/Menu"
import type {Weekday} from "../types/Weekday";
import type {Meal} from "../types/Meal";
import {useMeals} from "../hooks/useMeals";
import MealsButton from "../components/MealsButton";
import Spinner from "../components/Spinner";
import * as React from "react";
import {useAddMenus} from "../hooks/useMenus.ts";

export default function CreateMenu({onClose}: {onClose: () => void}) {
    const {meals, error, loading} = useMeals();
    const {menuToAdd, errorMenu, loadingMenu} = useAddMenus();
    const [menuMessage, setMenuMessage] = useState<string|null>(null);
    const [mealForDay, setMealForDay] = useState<Meal|null>(null)

    const handleMealForDay   = (meal: Meal|null) => {
        setMealForDay(meal);
    }

    const [nameOfMenu, setNameOfMenu] = useState("");

    const emptyWeek:Weekday[] = [
        {day: "monday", mealId: null, mealName: ""},
        {day: "tuesday", mealId: null, mealName: ""},
        {day: "wednesday", mealId: null, mealName: ""},
        {day: "thursday", mealId: null, mealName: ""},
        {day: "friday", mealId: null, mealName: ""},
        {day: "saturday", mealId: null, mealName: ""},
        {day: "sunday", mealId: null, mealName: ""},
    ];

    const [weekMeals, setWeekMeals] = useState(structuredClone(emptyWeek));

    const handleMealId = (index: number, mealForDay: Meal) => {

        setWeekMeals(week => week.map((day, i) =>
            i === index ? {...day, mealId: mealForDay.id, mealName: mealForDay.mealName} : day));
    };
    const removeMealId = (index:number) => {
        setWeekMeals(meal => meal.map((item, i) => i === index ? {...item, mealId: null, mealName: "" } : item));
    };

    const handleSubmit = async (e:React.FormEvent) => {
        e.preventDefault();

        if(!nameOfMenu.trim()){
            alert("Please type a name for your menu");
            return;
        }

        const checkNotNull = weekMeals.some(day => day.mealId === null);

        if (checkNotNull) {
            alert("All days must contain a meal");
            return;
        }

        const menu:Menu ={
            menuName: nameOfMenu,
            mealIds: weekMeals.map(id => id.mealId!)
        };

        const res = await menuToAdd(menu);
        setMenuMessage("Recipe of "+res.menuName+" successfully saved");
        setNameOfMenu("");
        setWeekMeals(structuredClone(emptyWeek));
    };

    return(
        <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
            <div className={"flex flex-col justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-6"}>
                <div className={"mt-4"}>
                    <MealsButton type={"button"} text={"Close"} onClick={onClose}/>
                    <div className={"flex flex-col"}>
                        <label className={"font-bold"}>Name of menu:</label>
                        <input className={"p-1 border rounded-md text-xl"}
                               type={"text"}
                               value={nameOfMenu}
                               onChange={(e) => setNameOfMenu(e.target.value)}
                               required={true}/>
                    </div>
                    <div className={"flex justify-end items-center"}>
                        {error && <p className={"mr-4 text-red-600"}>{error}</p>}
                        {loading && <Spinner/>}
                        <SelectMeal meals={meals} mealForDay={mealForDay} handleMealForDay={handleMealForDay}/>
                    </div>
                    <div>
                        <div>
                            {weekMeals.map((day, index) =>
                                <div key={index}>
                                    <label>{day.day}</label>
                                    <div className={"flex flex-row justify-center"}>
                                        <input className={"p-1 border rounded-md text-xl"}
                                               type={"text"}
                                               value={day.mealName || "No meal selected"}
                                               readOnly={true}/>

                                        <MealsButton type={"button"} text={"add"}
                                                     onClick={()=>{
                                                         if(!mealForDay) {
                                                             alert("Please select a meal");
                                                             return;
                                                         }
                                                         handleMealId(index, mealForDay)
                                                     }}/>
                                        <MealsButton type={"button"} text={"Remove"}
                                                     onClick={() => {removeMealId(index)}}/>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
                <form onSubmit={handleSubmit}>
                    <MealsButton type={"submit"} text={"Save menu"}/>
                    {menuMessage && <p>{menuMessage}</p>}
                    {errorMenu && <p>{error}</p>}
                    {loadingMenu && <Spinner/>}
                </form>
            </div>
        </div>
    )
}
