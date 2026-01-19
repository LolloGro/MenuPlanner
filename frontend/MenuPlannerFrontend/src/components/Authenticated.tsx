import {useAuth} from "../hooks/useAuth";

export default function Authenticated() {
    const auth = useAuth();

    if(auth === null) return <p>Checking if logged in</p>

    return <p>{auth ? "You again back" : "Please log in!"}</p>
}
