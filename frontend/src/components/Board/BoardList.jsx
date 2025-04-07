// src/components/Board/BoardList.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useSearchParams, useNavigate } from "react-router-dom";
import BoardItem from "./BoardItem";

const BoardList = () => {
  const [posts, setPosts] = useState([]);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const categoryId = searchParams.get("category");

  useEffect(() => {
    if (!categoryId) return;
    axios
      .get(`/boards/${categoryId}/posts`)
      .then((res) => setPosts(res.data))
      .catch((err) => console.error("게시글 로드 실패:", err));
  }, [categoryId]);

// src/components/Board/BoardList.jsx
const handleItemClick = (boardId) => {
  // 절대경로 말고 상대경로로
  // navigate(`../${boardId}?category=${categoryId}&boardId=${boardId}`);
  // 또는
  navigate(`/board/${boardId}?category=${categoryId}&boardId=${boardId}`);
};


  const isLoggedIn = Boolean(localStorage.getItem("accessToken"));

  return (
    <div className="space-y-4">
      {isLoggedIn && categoryId && (
        <div className="mb-4 text-right">
          <button
            onClick={() => navigate(`/board/write?category=${categoryId}`)}
            className="px-4 py-2 text-white transition bg-green-500 rounded hover:bg-green-600"
          >
            새 글 작성
          </button>
        </div>
      )}
      {posts.length === 0 && (
        <p className="text-center text-gray-500">게시글이 없습니다.</p>
      )}
      {posts.map((post) => (
        <BoardItem
          key={post.boardId}
          post={post}
          onClick={() => handleItemClick(post.boardId)}
        />
      ))}
    </div>
  );
};

export default BoardList;
