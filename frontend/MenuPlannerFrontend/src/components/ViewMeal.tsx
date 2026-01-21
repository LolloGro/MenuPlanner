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
        <section className={"relative z-10 w-full max-w-lg max-h-[80vh] bg-white rounded-lg shadow-lg flex flex-col"}>
            <MealsButton type={"button"} text={"Close"} onClick={onClose}/>
            <div>
                <p>{meal.mealName}</p>
                <p>{meal.mainIngredient}</p>
                <p>{meal.mealType}</p>
                <p>{meal.time}</p>
            </div>
            <MealsButton type={"button"} text={"View recipe"} onClick={showRecipe}/>
            <div>
                {loading && <p>{loading}</p>}
                {message && <p>{message}</p>}
                {recipe && <ViewRecipe ingredients={recipe.ingredients} description={recipe.description}/>}
                {error && <p>{error}</p>}
            </div>
        </section>
    )
}
