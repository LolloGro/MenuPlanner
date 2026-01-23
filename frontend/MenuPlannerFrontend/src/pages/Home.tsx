import Layout from "../components/Layout";
import HomePage from "../assets/HomePage250.png"

export default function Home(){
    return (
            <Layout>
                <section className="max-w-md min-w-xs p-6">
                        <h1 className="text-3xl font-bold mb-2">What's for dinner?</h1>
                        <p className="mb-2"> We help you remember meals you might otherwise forget
                            and make meal planning a little easier.</p>
                        <p> Here you can gather all your favorite dishes and recipes in one place.
                            Create weekly meal plans based on what you actually enjoy eating —
                            either on your own or by letting chance decide.</p>
                </section>
                <section className="flex flex-col justify-center mt-4">
                    <img src={HomePage} alt="Menu"/>
                </section>
            </Layout>
    )
}
