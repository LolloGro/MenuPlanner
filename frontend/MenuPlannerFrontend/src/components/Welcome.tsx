import {useAuth} from "../hooks/useAuth.tsx";

export default function Welcome() {
    const auth = useAuth();

    if(auth === null) return <p>Checking if logged in</p>

    return <p>{auth ? "Welcome back" : "Please log in!"}</p>
}
