import React from "react";
import Category from "../Home/Category";

const Home = () => {
  return (
    <main className="min-h-screen py-8 bg-gray-50">
      {/* Category 컴포넌트가 내부에서 그리드+카드를 모두 렌더링 */}
      <Category />
    </main>
  );
};

export default Home;
