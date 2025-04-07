import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../context/AuthContext";

const CommentList = ({ boardId, reload, setReload }) => {
  const [comments, setComments] = useState([]);
  const { auth } = useContext(AuthContext);
  const [editCommentId, setEditCommentId] = useState(null);
  const [editContent, setEditContent] = useState("");

  // 댓글 삭제 처리
  const handleDelete = async (commentId) => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      try {
        await axios.delete(`/comments/${commentId}`, {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        });
        // 삭제 후 목록 새로고침
        setReload((prev) => !prev);
      } catch (error) {
        console.error("댓글 삭제 실패:", error);
        alert("댓글 삭제 중 오류가 발생했습니다.");
      }
    }
  };
  const handleUpdate = async (commentId) => {
    if (!editContent.trim()) {
      alert("수정할 내용을 입력해주세요.");
      return;
    }
    console.log("업데이트 요청: commentId =", commentId, "editContent =", editContent);
    try {
      await axios.put(
        `/comments/${commentId}`,
        { commentContent: editContent }, // 수정: editContent 사용
        {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        }
      );
      setEditCommentId(null);
      setEditContent("");
      setReload((prev) => !prev);
    } catch (error) {
      console.error("댓글 수정 실패:", error);
      alert("댓글 수정 중 오류가 발생했습니다.");
    }
  };
  useEffect(() => {
    if (!boardId) return;
    axios
      .get(`/comments?boardId=${boardId}`)
      .then((res) => setComments([...res.data]))
      .catch((err) => console.error("댓글 로드 실패:", err));
  }, [boardId, reload]);

  return (
    <div className="space-y-4">
      {comments.length === 0 ? (
        <p className="text-center text-gray-500">등록된 댓글이 없습니다.</p>
      ) : (
        comments.map((comment) => (
          <div
            key={comment.commentId}
            className="p-4 rounded-lg shadow-sm bg-gray-50"
          >
            {editCommentId === comment.commentId ? (
              <>
                <input
                  type="text"
                  value={editContent}
                  onChange={(e) => setEditContent(e.target.value)}
                  className="w-full p-2 border rounded"
                />
                <div className="flex mt-2 space-x-2">
                  <button
                    onClick={() => handleUpdate(comment.commentId)}
                    className="px-3 py-1 text-white bg-green-500 rounded"
                  >
                    저장
                  </button>
                  <button
                    onClick={() => setEditCommentId(null)}
                    className="px-3 py-1 text-white bg-gray-500 rounded"
                  >
                    취소
                  </button>
                </div>
              </>
            ) : (
              <>
                <p className="text-gray-800">{comment.commentContent}</p>
                <div className="flex justify-between mt-2 text-xs text-gray-500">
                  <span>작성자: {comment.commentWriter}</span>
                  <span>
                    {new Date(comment.createdAt).toLocaleString()}
                  </span>
                </div>
                {String(auth.userId) === String(comment.commentWriter) && (
                  <div className="flex mt-2 space-x-2">
                    <button
                      onClick={() => {
                        setEditCommentId(comment.commentId);
                        setEditContent(comment.commentContent);
                      }}
                      className="px-2 py-1 text-white bg-blue-500 rounded"
                    >
                      수정
                    </button>
                    <button
                      onClick={() => handleDelete(comment.commentId)}
                      className="px-2 py-1 text-white bg-red-500 rounded"
                    >
                      삭제
                    </button>
                  </div>
                )}
              </>
            )}
          </div>
        ))
      )}
    </div>
  );
};

export default CommentList;
