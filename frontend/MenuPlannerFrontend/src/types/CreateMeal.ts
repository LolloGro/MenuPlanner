import type {MealType} from "./MealType";

export interface CreateMeal{
    mealName: string;
    mainIngredient: string;
    mealType: MealType;
    time: number;
}
