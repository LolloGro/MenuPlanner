import Layout from "../components/Layout";
import CreateMenu from "../components/CreateMenu";
import PageButton from "../components/PageButton";
import {useState} from "react";
import type {ViewOptions} from "../types/ViewOptions";
import ListOfMenu from "../components/ListOfMenu.tsx";

export default function Menu(){
    const [view, setView] = useState<ViewOptions>("NONE");

    return(
        <Layout>
            <div className={"flex flex-col justify-center items-center"}>
                <h2 className={"text-2xl font-bold flex justify-center m-4"}>Menus</h2>
                <PageButton type={"button"} text={"Create menu"} onClick={()=>setView("FORM")}/>
                {view === "FORM" && <CreateMenu onClose={() => setView("NONE")}/>}
                <p>Here you can create menus from your saved meals</p>
                <PageButton type={"button"} text={"View menus"} onClick={()=>setView("LIST")}/>
                {view === "LIST" && <ListOfMenu onClose={() => setView("NONE")}/>}
                <p>View all your saved menus </p>
            </div>
        </Layout>
    )
}
