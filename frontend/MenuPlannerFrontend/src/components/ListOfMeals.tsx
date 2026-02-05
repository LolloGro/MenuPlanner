import {useMeals} from "../hooks/useMeals";
import DefaultButton from "./DefaultButton.tsx";
import {useState} from "react";
import ViewMeal from "./ViewMeal";
import type {Meal} from "../types/Meal";
import Spinner from "./Spinner.tsx";
import SearchFiled from "./SearchFiled.tsx";

export default function ListOfMeals({onClose}:{onClose:() => void} ) {

    const {meals, error, loading} = useMeals();
    const [meal, setMeal] = useState<Meal | null>(null);
    const [searchFilter, setSearchFilter] = useState<string>("");

    function handleView(ref:Meal){
        setMeal(ref);
    }

    if(error){
        return <p>{error}</p>
    }

    if(loading){
        return <Spinner/>
    }

    if(meals.length === 0) {
        return <p>No meals stored yet!</p>
    }

   const filterMeals = meals.filter(meal =>
   meal.mealName.toLowerCase().includes(searchFilter.toLowerCase()));

    return (
        <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
            <div className={"flex flex-col justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-4"}>
                <div className={"flex flex-row justify-between items-center"}>
                    <h2 className={"text-2xl"}>List of meals</h2>
                    <DefaultButton type={"button"} text={"Close"} onClick={onClose}/>
                </div>
                <SearchFiled onSearch={setSearchFilter} inputText={"search meal"}/>
                <div className={"min-w-80 max-w-90 overflow-auto"}>
                    <ul>
                        {filterMeals.map(ref => (
                            <li key={ref.id}><DefaultButton type={"button"} text={"View"} onClick={() => handleView(ref)}></DefaultButton>{ref.mealName}</li>
                        ))}
                    </ul>
                </div>
                {meal && <ViewMeal meal={meal} onClose={() => setMeal(null)} />}
            </div>
        </div>
    );
}
