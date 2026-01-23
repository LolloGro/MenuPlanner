import type {Meal} from "../types/Meal";

export default function SelectMeal({meals, mealForDay ,handleMealForDay}: {meals: Meal[], mealForDay:Meal|null ,handleMealForDay:(meal: Meal|null) => void}) {

    return (
        <div>
            <select value={mealForDay?.id ?? ""} onChange={(e) =>{
                const id = Number(e.target.value);
                if(!id){
                    handleMealForDay(null);
                    return;
                }
                const mealOfChoice = meals.find((meal) => meal.id === id) ?? null;
                handleMealForDay(mealOfChoice);
            }}>
                <option>Välj måltid:</option>
                    {meals.map((meal:Meal) => (
                        <option value={meal.id} key={meal.id}>{meal.mealName}</option>
                    ))}
            </select>
        </div>
    )

}
