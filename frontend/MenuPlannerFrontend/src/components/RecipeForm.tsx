import {useState} from "react";
import type {Ingredient} from "../types/Ingredient";
import * as React from "react";
import MealsButton from "./MealsButton.tsx";

export default function RecipeForm() {

    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [newIngredient, setNewIngredient] = useState<Ingredient>(
        {ingredient:"", amount:0, measurement:""});

    const [description, setDescription] = useState<string>("");

   const handleAddIngredient = (e:React.FormEvent) => {
       e.preventDefault();
       setIngredients([...ingredients, newIngredient]);
       setNewIngredient({ingredient:"", amount:0, measurement:""});
   };

   const removeIngredient = (index:number) => {
       setIngredients(ingredients.filter((_,i) => i !== index));
   };

    return (
        <>
            {ingredients &&
                <div className={"bg-gray-300 flex flex-col items-center justify-center border-2 border-solid border-primary rounded-md"}>
                    <h2 className={"font-bold"}>Added ingredients</h2>
                    <ul>
                        {ingredients.map((ingredient: Ingredient, index: number) => (
                            <li key={index} className={"flex flex-row"}>
                                <p>{ingredient.ingredient}</p>
                                <p>{ingredient.amount}</p>
                                <p>{ingredient.measurement}</p>
                                <MealsButton type={"button"} text={"Remove"} onClick={() => removeIngredient(index)}/>
                            </li>
                        ))}
                    </ul>
                </div>}
            {description &&
                <div>
                    <h3>Description:</h3>
                    <p>{description}</p>
                </div>
                }
            <form className={"flex flex-col"} onSubmit={handleAddIngredient}>
                <label>Ingredient:</label>
                <input className={"p-1 border rounded-md"} type={"text"}
                       value={newIngredient.ingredient}
                       onChange={(e) => setNewIngredient({...newIngredient, ingredient: e.target.value})}
                       required={true}></input>
                <label>Amount:</label>
                <input className={"p-1 border rounded-md"} type={"number"} step={0.5} min={0}
                       value={newIngredient.amount}
                onChange={(e) => setNewIngredient({...newIngredient, amount: Number(e.target.value)})}
                ></input>
                <label>Measurement:</label>
                <input className={"p-1 border rounded-md"} type={"text"}
                       value={newIngredient.measurement}
                onChange={(e) => setNewIngredient({...newIngredient, measurement: e.target.value})}
                ></input>
                <MealsButton type={"submit"} text={"Add ingredient"}/>
            </form>
            <div className={"flex flex-col"}>
                <label>Description</label>
                <textarea className={"p-1 border rounded-md"}
                          value={description}
                          onChange={(e) => setDescription(e.target.value)}></textarea>
            </div>
        </>
    )
}
