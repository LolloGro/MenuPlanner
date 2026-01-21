import type {MealType} from "../types/MealType";
import type {CreateMeal} from "../types/CreateMeal";
import {useState} from "react";
import {useAddMeal} from "../hooks/useMeals";
import * as React from "react";
import MealsButton from "./MealsButton";
import type {ViewOptions} from "../types/ViewOptions";
import type {Meal} from "../types/Meal";

export default function MealForm({onYes, onNo}:{onYes:(meal: Meal) => void, onNo:() => void}) {

    const [newMeal, setNewMeal] = useState<CreateMeal>({
        mealName: "",
        mainIngredient: "",
        mealType: "DINNER",
        time: 0
    });

    const mealTypes: MealType[] = [
        "DINNER",
        "LUNCH",
        "BREAKFAST",
        "SNACK",
        "DESSERT"
    ]

    const {add, error, loading} = useAddMeal();

    const [message, setMessage] = useState<string|null>(null);
    const [meal, setMeal] = useState<Meal|null>(null);

    const saveMeal = async(e: React.FormEvent) => {
        e.preventDefault();
            const res = await add(newMeal);
            setMessage("Meal "+res.mealName +" successfully Saved!");
            setMeal(res);
            setNewMeal({mealName: "", mainIngredient: "", mealType: "DINNER", time: 0});
            setView("NONE");
    };

    const [view, setView] = useState<ViewOptions>("FORM");

    return (
        <div>
            {view === "FORM" &&
                <form className={"border-solid border-primary rounded-lg flex flex-col"} onSubmit={saveMeal}>
                    <label>Meal name:</label>
                    <input className={"p-1 border rounded-md text-2xl"}
                           type={"text"}
                           value={newMeal.mealName}
                           onChange={(e) => setNewMeal({...newMeal, mealName: e.target.value})}
                           required={true}></input>

                    <label>Main ingredient: </label>
                    <input className={"p-1 border rounded-md text-2xl"}
                           type={"text"}
                           value={newMeal.mainIngredient}
                           onChange={(e) => setNewMeal({...newMeal, mainIngredient: e.target.value})}
                           required={true}></input>

                    <label>Estimated time to cook:</label>
                    <input className={"p-1 border rounded-md text-2xl"}
                           type={"number"}
                           step={5}
                           value={newMeal.time}
                           onChange={(e) => setNewMeal({...newMeal, time: Number(e.target.value)})}
                           required={true} min={0}></input>

                    <label>Type of meal: </label>
                    <select className={"border rounded-md"}
                            value={newMeal.mealType}
                            onChange={(e) => setNewMeal({...newMeal, mealType: e.target.value as MealType})}
                            required={true}>
                        {mealTypes.map(meal => (
                            <option key={meal} value={meal}>{meal}</option>
                        ))}
                    </select>
                    <MealsButton type={"submit"} text={"Save"}/>
                    {loading && <p>{"Saving"}</p>}
                    {error && <p>{error}</p>}
                </form>}

            {message &&
            <div className={"flex flex-col justify-center items-center"}>
                <p>{message}</p>
                <p>Do you like to add a recipe to saved meal?</p>
                <div>
                    <MealsButton type={"button"} text={"Yes"} onClick={() => meal && onYes(meal)} />
                    <MealsButton type={"button"} text={"No"} onClick={() => {
                        setMessage(null); setMeal(null); onNo();
                    }}/>
                </div>
            </div>
        }
        </div>
    )
}
