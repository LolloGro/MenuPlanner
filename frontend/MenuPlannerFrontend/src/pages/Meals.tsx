import {useState} from "react";
import Layout from "../components/Layout";
import PageButton from "../components/PageButton.tsx";
import MealForm from "../components/MealForm";
import ListOfMeals from "../components/ListOfMeals";
import RecipeForm from "../components/RecipeForm";
import type {ViewOptions} from "../types/ViewOptions";
import type {Meal} from "../types/Meal";

export default function Meals() {

    const [view, setView] = useState<ViewOptions>("NONE");
    const [savedMeal, setSavedMeal] = useState<Meal | null>(null);

    const handleYes = (meal: Meal) => {
        setSavedMeal(meal);
        setView("RECIPE");
    };


    return (
    <Layout>
        <h2 className="text-2xl font-bold m-4">Meals</h2>
      <PageButton type={"button"} text={"Add meal"}  onClick={() =>setView("MEAL")}></PageButton>
        <p>Add your favorite meals, and optionally include a recipe</p>
        {view === "MEAL" && <MealForm onYes={handleYes}
                onNo={() => setView("NONE")}/> }
        {view === "RECIPE" && savedMeal && <RecipeForm meal={savedMeal} onClose={() => setView("NONE")}/>}
      <PageButton type={"button"} text={"View meals"} onClick={() => setView("LIST")}></PageButton>
        <p>View all your saved meals</p>
        {view === "LIST" && <ListOfMeals onClose={() => setView("NONE")}/>}
    </Layout>
)
}
