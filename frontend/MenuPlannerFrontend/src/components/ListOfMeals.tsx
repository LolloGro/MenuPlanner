import {useMeals} from "../hooks/useMeals";
import MealsButton from "./MealsButton";
import {useState} from "react";
import ViewMeal from "./ViewMeal";
import type {Meal} from "../types/Meal";

export default function ListOfMeals({onClose}:{onClose:() => void} ) {

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
        <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
            <div className={"flex flex-col justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-4"}>
                <div className={"flex flex-row justify-between items-center"}>
                    <h2 className={"text-2xl"}>List of meals</h2>
                    <MealsButton type={"button"} text={"Close"} onClick={onClose}/>
                </div>
                <div className={"min-w-80 max-w-90 overflow-auto"}>
                    <ul>
                        {meals.map(ref => (
                            <li key={ref.id}><MealsButton type={"button"} text={"View"} onClick={() => handleView(ref)}></MealsButton>{ref.mealName}</li>
                        ))}
                    </ul>
                </div>
                {meal && <ViewMeal meal={meal} onClose={() => setMeal(null)} />}
            </div>
        </div>
    );
}
