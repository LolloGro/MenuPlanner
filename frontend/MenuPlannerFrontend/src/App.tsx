import {BrowserRouter, Routes, Route} from "react-router-dom";
import Home from "./pages/Home";
import Meals from "./pages/Meals";
function App() {

  return (
  <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/meals" element={<Meals/>}/>
      </Routes>
  </BrowserRouter>
  )
}

export default App
