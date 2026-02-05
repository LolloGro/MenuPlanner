import {useEffect, useState} from "react";
import type {Menu, ReadMenu} from "../types/Menu.ts";
import {addMenu, getMenus} from "../service/menuService.ts";

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

export function useViewMenu() {
    const [menu, setMenu] = useState<ReadMenu[]>([]);
    const [errorViewMenu, setErrorViewMenu] = useState<string | null>(null);
    const [loadingViewMenu, setLoadingViewMenu] = useState<boolean>(true);

    useEffect(() => {
        getMenus().then(setMenu)
            .catch(error => setErrorViewMenu(error.message))
            .finally(() => setLoadingViewMenu(false));
    },[]);

    return {menu, errorViewMenu, loadingViewMenu};
}
