import Navbar from "../Navbar/Navbar";

const Header = () => {
  return (
    <header className="bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 py-6 text-center">
        <h1 className="text-4xl font-bold text-blue-600 tracking-tight">ReactTime</h1>
        <p className="text-gray-500 mt-2 text-lg">모던한 커뮤니티로 초대합니다</p>
      </div>
      <Navbar />
    </header>
  );
};

export default Header;
