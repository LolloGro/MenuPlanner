import {useState} from "react";
import type {Ingredient} from "../types/Ingredient";
import * as React from "react";

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
            <h2>Added ingredients</h2>
            {ingredients && <ul>
                {ingredients.map((ingredient: Ingredient, index: number) => (
                       <li key={index}>
                           <p>{ingredient.ingredient}</p>
                           <p>{ingredient.amount}</p>
                           <p>{ingredient.measurement}</p>
                           <button type={"button"} onClick={() => removeIngredient(index)}>Remove</button>
                       </li>
                ))}
            </ul>}
            {description && <p>{description}</p>}

            <form className={"flex flex-col"} onSubmit={handleAddIngredient}>
                <label>Ingredient:</label>
                <input className={"p-1 border rounded-md"} type={"text"}
                       value={newIngredient.ingredient}
                       onChange={(e) => setNewIngredient({...newIngredient, ingredient: e.target.value})}
                       required={true}></input>
                <label>Amount:</label>
                <input className={"p-1 border rounded-md"} type={"number"} step={0.5} min={0}
                onChange={(e) => setNewIngredient({...newIngredient, amount: Number(e.target.value)})}
                ></input>
                <label>Measurement:</label>
                <input className={"p-1 border rounded-md"} type={"text"}
                onChange={(e) => setNewIngredient({...newIngredient, measurement: e.target.value})}
                ></input>
                <button className={"bg-primary text-white rounded-md m-2 p-2 shadow-lg hover:text-2xl hover:cursor-pointer"} type={"submit"}>Add ingredient</button>
            </form>
            <div>
                <label>Description</label>
                <input className={"p-1 border rounded-md"} type={"text"} onChange={(e) => setDescription(desc => desc + e.target.value)}></input>
            </div>
        </>
    )
}
