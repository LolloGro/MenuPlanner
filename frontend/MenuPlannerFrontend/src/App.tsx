import {BrowserRouter, Routes, Route} from "react-router-dom";
import Home from "./pages/Home";
import Meals from "./pages/Meals";
import Login from "./pages/Login";
function App() {

  return (
  <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/meals" element={<Meals/>}/>
          <Route path="/login" element={<Login/>}/>
      </Routes>
  </BrowserRouter>
  )
}

export default App
