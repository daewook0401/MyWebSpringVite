import React, { useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../context/AuthContext";
import CommentList from "./CommentList";

const CommentForm = ({ boardId }) => {
  const { auth } = useContext(AuthContext);
  const [commentContent, setCommentContent] = useState("");
  const [reload, setReload] = useState(false);

  const handleInsertComment = async (e) => {
    e.preventDefault();
    if (!commentContent.trim()) {
      alert("내용을 작성해주세요.");
      return;
    }
    if (!auth.isAuthenticated) {
      alert("로그인이 필요합니다.");
      return;
    }

    try {
      await axios.post(
        `/comments`,
        {
          boardId: boardId,
          commentWriter: auth.userId,
          commentContent: commentContent,
        },
        {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        }
      );
      setCommentContent("");
      setReload((prev) => !prev);
    } catch (err) {
      console.error("댓글 등록 실패:", err);
      alert("댓글 등록 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="max-w-3xl p-4 mx-auto bg-white rounded-lg shadow">
      <h3 className="mb-3 text-xl font-semibold text-gray-800">댓글</h3>
      <form onSubmit={handleInsertComment} className="flex mb-6 space-x-2">
        <input
          type="text"
          placeholder="댓글을 입력하세요"
          value={commentContent}
          onChange={(e) => setCommentContent(e.target.value)}
          className="flex-1 p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
        />
        <button
          type="submit"
          className="px-4 py-2 text-white transition bg-blue-500 rounded hover:bg-blue-600"
        >
          작성
        </button>
      </form>

      {/* 댓글 리스트 */}
      <CommentList boardId={boardId} reload={reload} setReload={setReload} />
    </div>
  );
};

export default CommentForm;
