import type {Meal} from "./Meal.ts";

export interface Menu {
    menuName: string;
    mealIds: number[];
}

export interface ReadMenu{
    id: number;
    menuName: string;
    meals: Meal[];
    menuCreatedDate: Date;
}
