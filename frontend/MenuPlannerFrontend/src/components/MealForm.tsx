import type {MealType} from "../types/MealType";
import type {CreateMeal} from "../types/CreateMeal";
import {useState} from "react";
import {useAddMeal} from "../hooks/useMeals";
import * as React from "react";

export default function MealForm({open}:{open:string}) {

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

    const saveMeal = async(e: React.FormEvent) => {
        e.preventDefault();
        try{
            const res = await add(newMeal);
            setMessage(res.mealName +" successfully Saved!");
        }catch(error: any){
            setMessage(null);
        }
    };

    return (
        <div className={open}>
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
            <button type={"submit"} className={"bg-primary text-white rounded-md m-2 p-2 shadow-lg hover:text-2xl hover:cursor-pointer"}>Save</button>
                {loading && <p>{loading}</p>}
                {message && <p>{message}</p>}
                {error && <p>{error}</p>}
            </form>

        </div>
    )
}
