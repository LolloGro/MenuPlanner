import {isLoggedIn} from "../service/authService";
import {useEffect, useState} from "react";

export function useAuth() {
    const [authenticated, setAuthenticated] = useState<boolean>(false);

    useEffect(() => {
        isLoggedIn()
            .then(setAuthenticated);
    }, [])

    return authenticated;
}
