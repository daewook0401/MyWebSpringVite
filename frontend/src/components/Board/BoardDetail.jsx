// src/components/boardss/boardssDetail.jsx
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { useSearchParams, useNavigate, useParams } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import CommentForm from "../Comment/CommentForm";
import CommentList from "../Comment/CommentList";

const BoardDetail = () => {
  const [searchParams] = useSearchParams();
  const categoryId = searchParams.get("category");
  const boardId    = searchParams.get("boardId");
  const navigate   = useNavigate();
  const { auth }   = useContext(AuthContext);
  const isLoggedIn = auth.isAuthenticated;

  const [post, setPost] = useState(null);

  // 게시글 상세 로드
  useEffect(() => {
    if (!boardId) return;
    axios
      .get(`/boards/${boardId}`, { params: { category: categoryId } })
      .then(res => setPost(res.data))
      .catch(err => console.error("게시글 상세 로드 실패:", err));
  }, [boardId, categoryId]);

  const handleDelete = () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      axios
      .delete(`/boards/${boardId}`, {     headers: {
        Authorization: `Bearer ${auth.accessToken}`,
      }})
      .then(() => navigate(`/board?category=${categoryId}`))
      .catch(err => console.error("삭제 실패:", err));
    }
  };

  const handleEdit = () => {
    navigate(`/board/${boardId}/write?category=${categoryId}`)
  }
  if (!post) return <div>로딩 중...</div>;

  return (
    <div className="max-w-3xl p-6 mx-auto mt-12 bg-white rounded-lg shadow">
      {/* 게시글 */}
      <h1 className="mb-4 text-3xl font-bold text-gray-800">
        {post.boardTitle}
      </h1>
      <div className="flex items-center mb-6 space-x-4 text-sm text-gray-500">
        <span>작성자: {post.userName}</span>
        <span>카테고리: {categoryId}</span>
        <span>게시글ID: {boardId}</span>
        <span>작성일: {new Date(post.createdAt).toLocaleString()}</span>
      </div>
      <div className="mb-6 text-gray-700">
        {post.boardContent}
      </div>
      <img src={post.boardFileUrl} className="mb-6 prose text-gray-700">
      </img>
      
      <div className="flex mb-8 space-x-3">
        {String(auth.userId) === String(post.boardWriter) && (
        <>
          <button
            onClick={handleEdit}
            className="px-4 py-2 text-white transition bg-blue-500 rounded hover:bg-blue-600"
          >
            수정
          </button>
          <button
            onClick={handleDelete}
            className="px-4 py-2 text-white transition bg-red-500 rounded hover:bg-red-600"
          >
            삭제
          </button>
        </>
        )}
        
        <button
          onClick={() => navigate(`/board?category=${categoryId}`)}
          className="px-4 py-2 text-gray-700 transition bg-gray-200 rounded hover:bg-gray-300"
        >
          목록으로
        </button>
      </div>

      {/* 댓글 섹션 */}
      <CommentForm boardId={boardId}/>

    </div>
  );
};

export default BoardDetail;
