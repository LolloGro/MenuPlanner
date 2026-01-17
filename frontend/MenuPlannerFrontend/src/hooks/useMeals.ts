import {getMeals} from "../service/mealService";
import type {Meal} from "../types/Meal";
import {useEffect, useState} from "react";

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
