import type {CreateMeal} from "../types/CreateMeal";
import type {Meal} from "../types/Meal"

export async function getMeals ():Promise<Meal[]> {
    const res = await fetch("/api/meals", {
        method: "GET",
        credentials: "include",
        headers: {
            "Accept": "application/json",
        },
    });
/**
 * Fallback error message if res error is falsy
 * */

    if(!res.ok) {
        let message = "Failed to fetch Meals";
        try{
            const error = await res.json();
            message = error.message || message;
        }catch{}

        throw new Error(message);
    }

    return await res.json() as Meal[];
}

export async function addMeal (newMeal : CreateMeal) : Promise<Meal> {
    const res = await fetch("/api/meals", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newMeal),
    });

    if(!res.ok) {
        let message = "Failed to add Meal";
        try{
            const error = await res.json();
            message = error.message || message;
        }catch {}
        throw new Error(message);
    }

    return await res.json() as Meal;
}
