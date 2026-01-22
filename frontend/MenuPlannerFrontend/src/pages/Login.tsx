import Layout from "../components/Layout";
import GoogleLogo from "../assets/GoogleLogo.png"

export default function Login(){
    return (
        <Layout>
            <div className={"flex flex-col items-center"}>
                <h2 className={"text-2xl font-bold mt-4 mb-4"}>Login</h2>
                <div className={"mb-4"}>
                    <p>Sig in with Google to get started</p>
                    <p>A Google account is required to access and use all features</p>
                    <p>No passwords are stored.</p>
                </div>
                <a href={"/oauth2/authorization/google"}><img className={"hover:scale-110 focus:scale-110"} src={GoogleLogo} alt="Sign in with Google"/></a>
            </div>
        </Layout>
    )
}
