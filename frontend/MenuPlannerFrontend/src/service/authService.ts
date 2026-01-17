export async function isLoggedIn(): Promise<boolean> {
    const res = await fetch("/auth",{

        method: "GET",
        credentials: "include",
        headers: {
            "Accept": "application/json",
        },
    });

    if (!res.ok) {
        let message = "Please log in!";
        try{
            const error = await res.json();
            message = error.message;
        }catch{}
        console.warn(message);
        return false;
    }

    return true;
}
