import type {ReactNode} from "react";

export default function Link({href, children}:{href: string, children: ReactNode}) {
    return(<a className="p-2 hover:text-link-focus focus:text-link-focus" href={href}>
            {children}
        </a>)
}
