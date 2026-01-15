import type {ReactNode} from "react";
import Header from "./Header.tsx";
import Footer from "./Footer.tsx";

export default function Layout({children}: {children: ReactNode}) {
    return(
        <div className="min-h-screen flex flex-col ">
            <Header />
            <main className="max-w-7xl mx-auto flex-1 flex flex-col items-center">{children}</main>
            <Footer />
        </div>
    )
}
