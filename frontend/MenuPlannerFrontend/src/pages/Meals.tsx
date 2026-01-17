import {useState} from "react";
import Layout from "../components/Layout";
import Button from "../components/Button";
import MealForm from "../components/MealForm";

export default function Meals(){
const [open, setOpen] = useState("hidden");

function handleOpen() {
    if(open == "hidden"){
        setOpen("block");
    }else{
        setOpen("hidden");
    }
}
    return (
    <Layout>
      <Button type={"button"} text={"Add a meal"} onClick={() => handleOpen()}></Button>
        <MealForm open={open} />
    </Layout>
)
}
