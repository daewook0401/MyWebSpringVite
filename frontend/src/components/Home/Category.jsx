// src/components/Category/Category.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
// 별 아이콘 사용 (react-icons)
import { FaStar, FaRegStar } from "react-icons/fa";

const Category = () => {
  const [data, setData] = useState([]); // [{ categoryId, categoryName, posts: [] }, ...]
  const [favorites, setFavorites] = useState([]); // 예: 즐겨찾기된 categoryId 배열
  const navigate = useNavigate();

  // 카테고리 + 게시글 묶음 한 번에 가져오기
  useEffect(() => {
    axios
      .get("/categories", { params: { limit: 5 } })
      .then((res) => {
        setData(res.data);
      })
      .catch((err) => console.error("카테고리/게시글 로드 실패:", err));
  }, []);

  // 즐겨찾기 토글 함수 (임시 로컬 구현)
  // 실제로는 서버에 POST/DELETE 요청 후, favorites 상태 갱신
  const handleFavoriteToggle = (categoryId) => {
    // 이미 즐겨찾기된 카테고리라면 제거
    if (favorites.includes(categoryId)) {
      setFavorites((prev) => prev.filter((id) => id !== categoryId));
    } else {
      // 즐겨찾기에 추가
      setFavorites((prev) => [...prev, categoryId]);
    }
  };

  return (
    <div className="min-h-screen py-8 bg-gray-50">
      {/* 반응형 그리드 레이아웃 */}
      <div className="grid grid-cols-1 gap-6 px-4 mx-auto max-w-7xl sm:grid-cols-2 lg:grid-cols-3">
        {data.map((cat) => {
          const isFavorite = favorites.includes(cat.categoryId);
          return (
            <div
              key={cat.categoryId}
              className="relative flex flex-col p-4 bg-white rounded-lg shadow"
            >
              {/* 별 아이콘 버튼 (오른쪽 상단) */}
              <button
                onClick={() => handleFavoriteToggle(cat.categoryId)}
                className="absolute text-2xl top-3 right-3"
              >
                {isFavorite ? (
                  <FaStar className="text-yellow-400 transition hover:text-yellow-500" />
                ) : (
                  <FaRegStar className="text-gray-300 transition hover:text-yellow-400" />
                )}
              </button>

              {/* 카테고리 제목 */}
              <h3 className="mb-3 text-xl font-semibold text-left text-gray-800">
                {cat.categoryName}
              </h3>

              {/* 게시글 리스트 (왼쪽 정렬) */}
              <ul className="flex-1 space-y-2 text-left">
                {cat.posts.map((post) => (
                  <li key={post.id}>
                    <button
                      onClick={() => {
                        navigate(
                          `/board/${post.id}?category=${cat.categoryId}&boardId=${post.id}`
                        );
                      }}
                      className="text-blue-600 hover:underline"
                    >
                      {post.title}
                    </button>
                  </li>
                ))}
              </ul>

              {/* 더보기 버튼 (오른쪽 하단) */}
              <button
                onClick={() => navigate(`/board?category=${cat.categoryId}`)}
                className="self-end mt-4 text-sm text-gray-500 transition hover:text-blue-500"
              >
                더보기 &raquo;
              </button>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Category;
