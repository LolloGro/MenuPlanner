import {MealType} from "./MealType";

export interface Meal{
    id: number;
    mealName: string;
    mainIngredient: string;
    mealType: MealType;
    time: number;
}
