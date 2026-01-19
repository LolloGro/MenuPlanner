import {useState} from "react";
import {getRecipe} from "../service/recipeService";

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
