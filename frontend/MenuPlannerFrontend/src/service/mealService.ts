import type {Meal} from "../types/Meal"

export async function getMeals ():Promise<Meal[]> {
    const res = await fetch("/api/meals", {
        method: "GET",
        credentials: "include",
        headers: {
            "Accept": "application/json",
        },
    });

    if(!res.ok) {
        let message = "Failed to fetch Meals";
        try{
            const error = await res.json();
            message = error.message ?? message;
        }catch{}

        throw new Error(message);
    }

    return await res.json();
}
