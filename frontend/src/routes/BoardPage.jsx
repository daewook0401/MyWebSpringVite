// src/routes/BoardPage.jsx
import React from "react";
import { Routes, Route, Outlet } from "react-router-dom";
import BoardList   from "../components/Board/BoardList";
import BoardWrite  from "../components/Board/BoardWrite";
import BoardDetail from "../components/Board/BoardDetail";

const BoardPage = () => {
  return (
    <div>
      {/* 공통 레이아웃(예: 사이드바, 제목 등) 있으면 여기에 */}
      <Outlet />

      {/* 중첩 라우트 */}
      <Routes>
        {/* /board */}
        <Route index element={<BoardList />} />

        {/* /board/write */}
        <Route path="write" element={<BoardWrite />} />

        {/* /board/:id */}
        <Route path=":id" element={<BoardDetail />} />

        {/* /board/:id/edit */}
        <Route path=":id/write" element={<BoardWrite />} />
      </Routes>
    </div>
  );
};

export default BoardPage;
