import {useState} from "react";
import type {Ingredient} from "../types/Ingredient";
import * as React from "react";
import MealsButton from "./MealsButton.tsx";
import {useAddRecipe} from "../hooks/useRecipe";
import type {Recipe} from "../types/Recipe.ts";
import type {Meal} from "../types/Meal";

export default function RecipeForm({meal, onClose}:{meal:Meal, onClose: () => void}) {

    const mealId= meal.id;
    const mealName = meal.mealName;

    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [newIngredient, setNewIngredient] = useState<Ingredient>(
        {ingredient:"", amount:null, measurement:""});

    const [description, setDescription] = useState<string>("");

   const handleAddIngredient = (e:React.FormEvent) => {
       e.preventDefault();
       setIngredients([...ingredients, newIngredient]);
       setNewIngredient({ingredient:"", amount:null, measurement:""});
   };

   const removeIngredient = (index:number) => {
       setIngredients(ingredients.filter((_,i) => i !== index));
   };

    const {add, error, loading} = useAddRecipe();

    const saveRecipe = async (e: React.FormEvent) => {
        e.preventDefault();

        const recipe:Recipe = {
            ingredients: ingredients,
            description:description,
        }

        await add({mealId, recipe});
        onClose();
    };


    return (
        <>
            <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
                    <div className={"flex justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-6"}>
                        <div className={"min-w-80 max-w-90 max-h-screen overflow-auto"}>

                            <form className={"flex flex-col"} onSubmit={handleAddIngredient}>
                                <h2 className={"font-bold text-2xl"}>Create recipe for {mealName}</h2>
                                <label>Ingredient:</label>
                                <input className={"p-1 border rounded-md"} type={"text"}
                                       value={newIngredient.ingredient}
                                       onChange={(e) => setNewIngredient({...newIngredient, ingredient: e.target.value})}
                                       required={true}></input>
                                <label>Amount:</label>
                                <input className={"p-1 border rounded-md"} type={"number"} step={0.5} min={0.5}
                                       value={newIngredient.amount ?? ""}
                                       onChange={(e) =>
                                           setNewIngredient({...newIngredient, amount: e.target.value === "" ? null: Number(e.target.value)})}
                                ></input>
                                <label>Measurement:</label>
                                <input className={"p-1 border rounded-md"} type={"text"}
                                       value={newIngredient.measurement}
                                       onChange={(e) => setNewIngredient({...newIngredient, measurement: e.target.value})}
                                ></input>
                                <MealsButton type={"submit"} text={"Add ingredient"}/>
                                <label>Description</label>
                                <textarea className={"p-1 border rounded-md h-50"}
                                          value={description}
                                          onChange={(e) => setDescription(e.target.value)}></textarea>
                            </form>

                            <div className={"bg-gray-200 flex flex-col items-center justify-center border-1 border-solid border-primary rounded-md mt-2 mb-2"}>
                                <h2 className={"font-bold"}>Added ingredients:</h2>
                                <ul className={"p-2"}>
                                    {ingredients.map((ingredient: Ingredient, index: number) => (
                                        <li key={index} className={"grid grid-cols-2 items-center"}>
                                            <div className={"grid_span-1 flex flex-row gap-1"}>
                                                <p>{ingredient.ingredient}</p>
                                                <p>{ingredient.amount}</p>
                                                <p>{ingredient.measurement}</p>
                                            </div>
                                            <div className={"grid-span-2"}>
                                                <MealsButton  type={"button"} text={"Remove"} onClick={() => removeIngredient(index)}/>
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            </div>

                            <form className={"flex flex-col justify-center items-center"} onSubmit={saveRecipe}>
                                <div>
                                    <MealsButton type={"submit"} text={"Save"}/>
                                    <MealsButton type={"button"} text={"Close"} onClick={onClose}/>
                                </div>
                                {loading && <p>{"Saving"}</p>}
                                {error && <p>{error}</p>}
                            </form>

                        </div>
                </div>
            </div>
        </>
    )
}
