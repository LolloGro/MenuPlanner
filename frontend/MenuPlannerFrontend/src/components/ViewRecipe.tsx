import type {Ingredient} from "../types/Ingredient";

export default function ViewRecipe({ingredients, description}: {ingredients: Ingredient[], description: string}) {
    return(
        <div className={"pl-2 pr-2"}>
            <h2 className={"font-bold"}>Ingrediens:</h2>
            {ingredients.map((ingredient: Ingredient, index: number) => (
                <div key={index} className={"flex flex-row gap-1"}>
                    <p>{ingredient.ingredient}</p>
                    <p>{ingredient.amount}</p>
                    <p>{ingredient.measurement}</p>
                </div>
            ))}
            <h3 className={"font-bold"}>Description:</h3>
            <textarea className={"p-1 w-full h-50"} readOnly={true}>{description}</textarea>
        </div>
    )
}
