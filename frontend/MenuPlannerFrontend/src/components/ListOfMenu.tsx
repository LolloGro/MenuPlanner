import {useViewMenu} from "../hooks/useMenus.ts";
import {useState} from "react";
import type {ReadMenu} from "../types/Menu.ts";
import DefaultButton from "./DefaultButton.tsx";
import ViewMenu from "./ViewMenu.tsx";
import Spinner from "./Spinner.tsx";
import SearchFiled from "./SearchFiled.tsx";

export default function ListOfMenu({onClose}:{onClose:() => void}) {

    const {menu, errorViewMenu, loadingViewMenu} = useViewMenu();
    const [viewMenu, setViewMenu] = useState<ReadMenu | null>(null);
    const [searchFilter, setSearchFilter] = useState<string>("");

    function handleView(ref:ReadMenu){
        setViewMenu(ref);
    }

    if(errorViewMenu){
        return <p>{errorViewMenu}</p>
    }

    if(loadingViewMenu){
        return <Spinner/>
    }

    if(menu.length === 0) {
        return <p>No menus stored yet!</p>
    }

    const filterMenus = menu.filter(m =>
        m.menuName.toLowerCase().includes(searchFilter.toLowerCase()));

    return (
        <div className={"fixed inset-0 bg-black/50 flex items-center justify-center z-50"}>
            <div className={"flex flex-col justify-center max-h-screen min-w-100 bg-white rounded-lg shadow-xl p-4"}>
                <div className={"flex flex-row justify-between items-center"}>
                    <h2 className={"text-2xl"}>List of menus</h2>
                    <DefaultButton type={"button"} text={"Close"} onClick={onClose}/>
                </div>
                <SearchFiled inputText={"search menu"} onSearch={setSearchFilter}/>
                <div className={"min-w-80 max-w-90 overflow-auto"}>
                    <ul>
                        {filterMenus.map(ref => (
                            <li key={ref.id}><DefaultButton type={"button"} text={"View"} onClick={() => handleView(ref)}></DefaultButton>{ref.menuName}</li>
                        ))}
                    </ul>
                </div>
                {viewMenu && <ViewMenu menu={viewMenu} onClose={() => setViewMenu(null)} />}
            </div>
        </div>
    );
}
