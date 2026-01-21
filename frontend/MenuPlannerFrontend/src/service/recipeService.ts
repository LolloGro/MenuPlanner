import type {Recipe} from "../types/Recipe";

export async function getRecipe (recipeId: number): Promise<Recipe | null> {
    const res = await fetch(`/api/meals/${recipeId}/recipes`, {
        method: "GET",
        credentials: "include",
        headers: {
            "Accept": "application/json",
        },
    });

    if(!res.ok) {
        let message = "Failed to fetch recipe";
        try{
            const error = await res.json();
            message = error.message || message;
        }catch{}

        throw new Error(message);
    }

    if(res.status === 204){
        return null;
    }

    return await res.json() as Recipe;
}

export async function addRecipe ({mealId, recipe}:{mealId:number, recipe: Recipe}): Promise<Recipe> {
    const res = await fetch(`/api/meals/${mealId}/recipes`, {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(recipe),
    })

    if(!res.ok) {
        let message = "Failed to add recipe";
        try{
            const error = await res.json();
            message = error.message || message;
        }catch {}
        throw new Error(message);
    }

    return await res.json() as Recipe;
}
