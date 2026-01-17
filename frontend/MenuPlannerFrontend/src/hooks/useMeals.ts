import {getMeals} from "../service/mealService";
import{addMeal} from "../service/mealService";
import type {Meal} from "../types/Meal";
import {useEffect, useState} from "react";
import type {CreateMeal} from "../types/CreateMeal.ts";

export function useMeals() {
    const [meals, setMeals] = useState<Meal[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getMeals().then(setMeals)
            .catch(error => setError(error.message))
            .finally(() => setLoading(false));
    },[]);

    return {meals, error, loading};
}

export function  useAddMeal() {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const add = async (newMeal: CreateMeal) => {
        setError(null);
        setLoading(true);
        try{
            const result = await addMeal(newMeal);
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
