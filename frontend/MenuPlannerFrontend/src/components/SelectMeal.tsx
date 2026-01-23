import type {Meal} from "../types/Meal";

export default function SelectMeal({meals, handleMealForDay}: {meals: Meal[], handleMealForDay:(meal: Meal) => void}) {

    return (
        <div>
            <ul>
                {meals.map((meal:Meal) => (
                    <li key={meal.id}>
                       <button type={"button"} onClick={() =>handleMealForDay(meal)}>{meal.mealName}</button>
                    </li>
                ))}
            </ul>
        </div>
    )

}
