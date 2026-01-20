import {useState} from "react";
import {getRecipe} from "../service/recipeService";
import type {Recipe} from "../types/Recipe";
import {addRecipe} from "../service/recipeService";

export function useGetRecipe(recipeId: number) {

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const get = async () => {
        setError(null);
        setLoading(true);
        try{
            const result = await getRecipe(recipeId);
            setLoading(false);
            return result;
        }catch(error:any){
            setError(error.message);
            setLoading(false);
            throw error;
        }
    };

    return {get, error, loading};
}

export function useAddRecipe() {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const add = async ({mealId, recipe}:{mealId:number, recipe:Recipe}) => {
        setError(null);
        setLoading(true);

        try{
            const  result = await addRecipe({mealId, recipe})
            setLoading(false);
            return result;
        }catch(error:any){
            setError(error.message);
            setLoading(false);
            throw error;
        }
    };

    return {add, error, loading};
}
