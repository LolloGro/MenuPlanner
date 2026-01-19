import {useMeals} from "../hooks/useMeals";
import MealsButton from "./MealsButton";
import {useState} from "react";
import ViewMeal from "./ViewMeal";
import type {Meal} from "../types/Meal";

export default function ListOfMeals({openList}: {openList:string}) {

    const {meals, error, loading} = useMeals();
    const [meal, setMeal] = useState<Meal | null>(null);

    function handleView(ref:Meal){
        setMeal(ref);
    }

    if(error){
        return <p>{error}</p>
    }

    if(loading){
        return <p>Loading...</p>
    }

    if(meals.length === 0) {
        return <p>No meals stored yet!</p>
    }

    return (
        <>
            <div className={openList}>
            <h2>List of meals:</h2>
            <ul>
                {meals.map(ref => (
                    <li key={ref.id}>{ref.mealName}<MealsButton type={"button"} text={"Views"} onClick={() => handleView(ref)}></MealsButton></li>
                ))}
            </ul>
        </div>
            <div>
                {meal && <ViewMeal meal={meal} onClose={() => setMeal(null)} />}
            </div>
        </>

    );
}
