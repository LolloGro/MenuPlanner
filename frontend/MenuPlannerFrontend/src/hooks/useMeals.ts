import {getMeals} from "../service/mealService.tsx";
import type {Meal} from "../types/Meal";
import {useEffect, useState} from "react";

export function useMeals() {
    const [meals, setMeals] = useState<Meal[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getMeals().then(setMeals)
            .catch(error => setError(error.message));
    },[]);

    return {meals, error};
}
