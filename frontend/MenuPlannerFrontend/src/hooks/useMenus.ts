import {useState} from "react";
import type {Menu} from "../types/Menu.ts";
import {addMenu} from "../service/menuService.ts";

export function useAddMenus() {
    const [errorMenu, setErrorMenu] = useState<string | null>(null);
    const [loadingMenu, setLoadingMenu] = useState<boolean>(false);

    const menuToAdd = async (menu: Menu) => {
        setErrorMenu(null);
        setLoadingMenu(true);

        try{
            const result = await addMenu(menu);
            setLoadingMenu(false);
            return result;
        }catch(error:any){
            setErrorMenu(error.message);
            setLoadingMenu(false);
            throw error;
        }
    };

    return {menuToAdd, errorMenu, loadingMenu};
}
