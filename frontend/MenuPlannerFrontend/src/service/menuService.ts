import type {Menu, ReadMenu} from "../types/Menu";

export async function addMenu(menu:Menu):Promise<ReadMenu> {
    const res = await fetch("/api/menus", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(menu),
    });

    if(!res.ok) {
        let message = "Failed to add menu";
        try{
            const error = await res.json();
            message = error.message || message;
        }catch {}
        throw new Error(message);
    }

    return await res.json() as ReadMenu;
}
