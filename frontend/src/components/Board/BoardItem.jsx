// src/components/Board/BoardItem.jsx
import React from "react";

const BoardItem = ({ post, onClick }) => {
  // post: { boardId, boardTitle, boardWriter, boardContent, boardCategory, createdAt, updatedAt }
  return (
    <div
      onClick={onClick}
      className="p-4 transition bg-white rounded shadow cursor-pointer hover:bg-gray-50"
    >
      <h2 className="text-xl font-semibold text-gray-800">
        {post.boardTitle}
      </h2>
      <p className="mt-1 text-sm text-gray-600">
        작성자: {post.userName}
      </p>
      <p className="text-xs text-gray-400">
        작성일: {new Date(post.createdAt).toLocaleString()}
      </p>
    </div>
  );
};

export default BoardItem;
