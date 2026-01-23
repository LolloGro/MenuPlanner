import type {Ingredient} from "./Ingredient";

export interface Recipe {
    ingredients: Ingredient[];
    description: string
}
