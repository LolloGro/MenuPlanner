import type {Meal} from "../types/Meal";
import {useGetRecipe} from "../hooks/useRecipe";
import type {Recipe} from "../types/Recipe";
import {useState} from "react";
import MealsButton from "./MealsButton";
import ViewRecipe from "./ViewRecipe";

export default function ViewMeal({meal, onClose}:{meal:Meal, onClose: () => void}) {

    const {get, error, loading} = useGetRecipe(meal.id);

    const [recipe, setRecipe] = useState<Recipe|null>(null);
    const [message, setMessage] = useState<string|null>(null);

    const showRecipe = async () => {
        setMessage(null);
        setRecipe(null);

        const res = await get();
            if(res === null) {
                setMessage("No recipe added to meal");
                return;
            }
            setRecipe(res);
    }

    return(
        <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
            <div className={"flex flex-col justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-4"}>
                <div className={"flex justify-end"}>
                    <MealsButton type={"button"} text={"Close"} onClick={onClose}/>
                </div>
                <div className={"overflow-auto"}>
                    <div>
                        <p className={"text-xl font-bold"}>{meal.mealName}</p>
                        <p>Main ingredient: {meal.mainIngredient}</p>
                        <p>Meal type: {meal.mealType}</p>
                        <p>Cooking time: {meal.time} min</p>
                    </div>
                    <MealsButton type={"button"} text={"View recipe"} onClick={showRecipe}/>
                    <div>
                        {loading && <p>Loading</p>}
                        {message && <p>{message}</p>}
                        {recipe && <ViewRecipe ingredients={recipe.ingredients} description={recipe.description}/>}
                        {error && <p>{error}</p>}
                    </div>
                </div>

            </div>
        </div>

    )
}
