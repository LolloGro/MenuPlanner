import {useState} from "react";
import SelectMeal from "../components/SelectMeal";
import type {Menu} from "../types/Menu"
import type {Weekday} from "../types/Weekday";
import type {Meal} from "../types/Meal";
import {useMeals} from "../hooks/useMeals";
import MealsButton from "../components/MealsButton";
import Spinner from "../components/Spinner";
import * as React from "react";

export default function CreateMenu(){
    const {meals, error, loading} = useMeals();
    const [mealForDay, setMealForDay] = useState<Meal|null>(null)

    const handleMealForDay   = (meal: Meal|null) => {
        setMealForDay(meal);
    }

    const [nameOfMenu, setNameOfMenu] = useState("");

    const [weekMeals, setWeekMeals] = useState<Weekday[]>([
        {day: "monday", mealId: null, mealName: ""},
        {day: "tuesday", mealId: null, mealName: ""},
        {day: "wednesday", mealId: null, mealName: ""},
        {day: "thursday", mealId: null, mealName: ""},
        {day: "friday", mealId: null, mealName: ""},
        {day: "saturday", mealId: null, mealName: ""},
        {day: "sunday", mealId: null, mealName: ""},
    ]);

    const handleMealId = (index: number, mealForDay: Meal) => {

        setWeekMeals(week => week.map((day, i) =>
            i === index ? {...day, mealId: mealForDay.id, mealName: mealForDay.mealName} : day));
    };
    const removeMealId = (index:number) => {
        setWeekMeals(meal => meal.map((item, i) => i === index ? {...item, mealId: null, mealName: "" } : item));
    };

    const handleSubmit = (e:React.FormEvent) => {
        if(!nameOfMenu.trim()){
            e.preventDefault();
            alert("Please type a name for your menu");
            return;
        }

        const checkNotNull = weekMeals.some(day => day.mealId === null);

        if (checkNotNull) {
            e.preventDefault();
            alert("All days must contain a meal");
            return;
        }

        const menu:Menu ={
            menuName: nameOfMenu,
            mealIds: weekMeals.map(id => id.mealId!)
        };

        console.log(menu);

    };

    return(
            <div>
                <div className={"mt-4"}>
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
                </form>
            </div>
    )
}
