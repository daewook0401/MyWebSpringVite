import './App.css'
import Home from "./components/Home/Home";
import Footer from "./components/modules/Footer/Footer";
import { Routes, Route } from "react-router-dom";
import Header from './components/modules/Header/Header';
import Board from './components/Board/BoardList';
import CategoryList from './components/Category/CategoryList'
import CategoryPage from './routes/CategoryPage'
import BoardPage from './routes/BoardPage';
import Join from './components/User/Join/Join';
import Login from './components/User/Login/Login';
import { AuthProvider } from './components/context/AuthContext';
function App() {
  return (
    <>
      <AuthProvider>
        <Header />
        <div className="main-section">
          <Routes>
            <Route path="/" element={<Home/>} />
            <Route path="/categoryList" element={<CategoryList />} />
            <Route path="/board/*" element={<BoardPage/>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/join" element={<Join />} />
            <Route path="categories/*" element={<CategoryPage/>}/>
            <Route path='*' element={<h1>404-page</h1>}/>
          </Routes>
        </div>
        <Footer />
      </AuthProvider>
    </>
  )
}

export default App
