// src/components/Navbar/Navbar.jsx
import NavItem from "./NavItem.jsx";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext.jsx";
import { useContext } from "react";

const Navbar = () => {
  const navi = useNavigate();
  const { auth, logout } = useContext(AuthContext);
  return (
    <nav className="bg-white shadow-md">
      <div className="px-4 mx-auto max-w-7xl sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Left nav */}
          <ul className="flex space-x-6">
            <NavItem onClick={() => navi("/")}>Home</NavItem>
            <NavItem onClick={() => navi("/categoryList")}>게시판목록</NavItem>
            <NavItem onClick={() => navi("/board/search")}>게시판검색</NavItem>
            <NavItem onClick={() => navi("/notice")}>공지사항</NavItem>
          </ul><p></p>

          {/* Right nav */}
          <div className="hidden space-x-4 sm:flex">
            {auth.isAuthenticated ? (
              <>
                <button
                  onClick={() => navi("/myinfo")}
                  className="px-4 py-2 font-semibold text-white transition bg-blue-500 rounded-md hover:bg-blue-600"
                >
                  내정보
                </button>
                <button
                  onClick={logout}
                  className="px-4 py-2 font-semibold text-white transition bg-blue-500 rounded-md hover:bg-blue-600"
                >
                  로그아웃
                </button>
              </>
            ) : (
              <>
                <button
                  onClick={() => navi("/login")}
                  className="px-4 py-2 font-semibold text-white transition bg-blue-500 rounded-md hover:bg-blue-600"
                >
                  로그인
                </button>
                <button
                  onClick={() => navi("/join")}
                  className="px-4 py-2 font-semibold text-white transition bg-blue-500 rounded-md hover:bg-blue-600"
                >
                  회원가입
                </button>
            </>
            )}
            
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
