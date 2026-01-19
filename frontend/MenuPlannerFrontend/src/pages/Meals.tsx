import {useState} from "react";
import Layout from "../components/Layout";
import PageButton from "../components/PageButton.tsx";
import MealForm from "../components/MealForm";
import ListOfMeals from "../components/ListOfMeals";

export default function Meals() {
    const [open, setOpen] = useState("hidden");
    const [openList, setOpenList] = useState("hidden");

    function handleOpenForm() {
        if (open == "hidden") {
            setOpen("visible");
        } else {
            setOpen("hidden");
        }
    }

    function handleOpenList() {
        if (openList == "hidden") {
            setOpenList("visible");
        } else {
        setOpenList("hidden");
        }
    }
    return (
    <Layout>
        <h2 className="text-2xl font-bold">Meals</h2>
        <p>Add your favorite meal, and optionally include a recipe</p>
      <PageButton type={"button"} text={"Add meal"} onClick={() => handleOpenForm()}></PageButton>
        <MealForm open={open} />
        <p>View all your saved meals</p>
      <PageButton type={"button"} text={"View meals"} onClick={() => handleOpenList()}></PageButton>
        <ListOfMeals openList={openList} />
    </Layout>
)
}
