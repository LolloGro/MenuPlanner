import {isLoggedIn} from "../service/authService.ts";
import {useEffect, useState} from "react";

export function useAuth() {
    const [authenticated, setAuthenticated] = useState<boolean| null>(null);

    useEffect(() => {
        isLoggedIn()
            .then(setAuthenticated)
            .catch(() => setAuthenticated(false));
    }, [])

    return authenticated;
}
