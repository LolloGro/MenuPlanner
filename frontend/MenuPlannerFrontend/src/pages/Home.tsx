import Layout from "../components/Layout.tsx";
import Box from "../components/Box.tsx";

export default function Home(){
    return (
            <Layout>
                <section className="max-w-md min-w-xs mt-6 mb-6">
                        <h1 className="text-3xl font-bold mb-2">What's for dinner?</h1>
                        <p className="mb-2"> We help you remember meals you might otherwise forget
                            and make meal planning a little easier.</p>
                        <p> Here you can gather all your favorite dishes and recipes in one place.
                            Create weekly meal plans based on what you actually enjoy eating —
                            either on your own or by letting chance decide.</p>
                </section>
                <section className="max-w-xl min-w-xs flex flex-row gap-4 flex-wrap mb-4">
                    <Box href={"/meals"}>
                        <p>Add a meal</p>
                    </Box>
                    <Box href={"/menus"}>
                        <p>A Create a menu</p>
                    </Box>
                </section>
            </Layout>
    )
}
