import {useMeals} from "../hooks/useMeals.ts";

export default function ListOfMeals() {

    const {meals, error, loading} = useMeals();

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
        <div>
            <h2>List of meals:</h2>
            <ul>
                {meals.map(meal => (
                    <li key={meal.id}>{meal.mealName}</li>
                ))}
            </ul>
        </div>
    );
}
