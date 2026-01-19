import type {Ingredient} from "../types/Ingredient";

export default function ViewRecipe({ingredients, description}: {ingredients: Ingredient[], description: string}) {
    return(
        <div>
            {ingredients.map(ingredient => (
                <div>
                    <p>{ingredient.ingredient}</p>
                    <p>{ingredient.amount}</p>
                    <p>{ingredient.measurement}</p>
                </div>
            ))}
            <p>{description}</p>
        </div>
    )
}
