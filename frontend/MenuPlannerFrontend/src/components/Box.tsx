import type {ReactNode} from "react";

export default function Box({href, children}:{href: string, children: ReactNode}) {
    return(
        <a href={href} className="border-solid border-3 border-primary flex flex-col items-center rounded-md w-40 h-40 p-2 shadow-md hover:border-5">
            {children}
        </a>
    )
}
