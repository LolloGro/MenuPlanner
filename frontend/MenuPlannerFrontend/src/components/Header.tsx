import menu from "../assets/Menu.png";
import Link from "./Link";
import {useAuth} from "../hooks/useAuth";

export default function Header() {

    const isAuthenticated = useAuth();

    return(<header className="bg-primary text-white shadow-md">
        <div className="flex flex-row items-center">
            <img src={menu} alt="Menu logo" className="p-1 ml-4"/>
            <div className="ml-4">
                <a href="/" className="text-5xl">Menu Planner</a>
                <p>Your meals, your menus</p>
            </div>

        </div>
        <nav className="bg-secondary  w-full">
            <ul className="flex justify-center gap-8">
                <li><Link href="/">Home</Link></li>
                <li><Link href={"/meals"}>Meals</Link></li>
                <li><Link href={"/menus"}>Menus</Link></li>
                {isAuthenticated ? (<li><Link href={"/logout"}>Logout</Link></li>) : (<li><Link href={"/login"}>Login</Link></li>)}
            </ul>
        </nav>
    </header>)
}
