import {useState} from "react";
import Layout from "../components/Layout";
import PageButton from "../components/PageButton.tsx";
import MealForm from "../components/MealForm";
import ListOfMeals from "../components/ListOfMeals";
import RecipeForm from "../components/RecipeForm";
import type {ViewOptions} from "../types/ViewOptions";

export default function Meals() {

    const [view, setView] = useState<ViewOptions>("NONE");
    const [id, setId] = useState<number | null>(null);
//
    return (
    <Layout>
        <h2 className="text-2xl font-bold">Meals</h2>
        <p>Add your favorite meal, and optionally include a recipe</p>
      <PageButton type={"button"} text={"Add meal"} onClick={() =>setView("MEAL")}></PageButton>
        {view === "MEAL" && <MealForm addRecipe={(res) => {setId(res); setView("RECIPE");}}/> }
        {view === "RECIPE" && id && <RecipeForm recipeId={id}/>}
        <h3>View all your saved meals</h3>
      <PageButton type={"button"} text={"View meals"} onClick={() => setView("LIST")}></PageButton>
        {view === "LIST" && <ListOfMeals />}
    </Layout>
)
}
