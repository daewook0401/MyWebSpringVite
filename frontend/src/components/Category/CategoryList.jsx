// src/components/Category/CategoryList.jsx
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

const PAGE_SIZE = 10;

const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [favorites, setFavorites] = useState([]);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const navigate = useNavigate();
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    let isMounted = true;
    axios
      .get("/categories", {
        params: { page, size: PAGE_SIZE },
      })
      .then((res) => {
        if (!isMounted) return;
        const newCats = res.data;
        setCategories((prev) => [...prev, ...newCats]);
        if (newCats.length < PAGE_SIZE) {
          setHasMore(false);
        }
      })
      .catch((err) => console.error("카테고리 로드 실패:", err));
    return () => {
      isMounted = false;
    };
  }, [page]);

  // 로그인한 경우, 사용자의 즐겨찾기 카테고리 로드
  useEffect(() => {
    if (!auth.isAuthenticated) {
      axios
        .get("/categories/favorites", {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        })
        .then((res) => {
          // 즐겨찾기된 카테고리 ID 배열이라고 가정
          setFavorites(res.data);
        })
        .catch((err) => console.error("즐겨찾기 로드 실패:", err));
    }
  }, [auth]);

  const handleMore = () => {
    setPage((prev) => prev + 1);
  };

  // 카테고리 삭제 처리 (작성자와 현재 사용자 일치 시)
  const handleDeleteCategory = (categoryId) => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      axios
        .delete(`/categories/${categoryId}`, {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        })
        .then(() => {
          setCategories(categories.filter((cat) => cat.categoryId !== categoryId));
        })
        .catch((err) => console.error("카테고리 삭제 실패:", err));
    }
  };

  // 즐겨찾기 토글 처리
  const handleFavoriteToggle = (categoryId) => {
    if (!auth || !auth.isAuthenticated) {
      alert("로그인이 필요합니다.");
      return;
    }
    if (favorites.includes(categoryId)) {
      // 즐겨찾기 제거
      axios
        .delete(`/categories/favorite/${categoryId}`, {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        })
        .then(() => {
          setFavorites(favorites.filter((id) => id !== categoryId));
        })
        .catch((err) => console.error("즐겨찾기 제거 실패:", err));
    } else {
      // 즐겨찾기 추가
      axios
        .post(`/categories/favorite/${categoryId}`, null, {
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        })
        .then(() => {
          setFavorites([...favorites, categoryId]);
        })
        .catch((err) => console.error("즐겨찾기 추가 실패:", err));
    }
  };

  return (
    <div className="max-w-xl p-6 mx-auto mt-12 bg-white rounded-lg shadow-lg">
      {/* 상단 영역: 제목과 카테고리 추가 버튼 */}
      <div className="flex items-center justify-between pb-3 mb-6 border-b">
        <h2 className="text-2xl font-bold text-gray-800">게시판 목록</h2>
        {auth.isAuthenticated && (
          <button
            onClick={() => navigate("/categories/new")}
            className="px-4 py-2 text-sm font-medium text-green-700 transition bg-green-100 rounded hover:bg-green-200"
          >
            + 게시판 추가
          </button>
        )}
      </div>

      {/* 카테고리 목록 */}
      <ul className="space-y-3">
        {categories.map((cat) => (
          <li
            key={cat.categoryId}
            className="flex items-center justify-between p-4 transition rounded shadow-sm bg-gray-50 hover:bg-gray-100"
          >
            <button
              onClick={() => navigate(`/board?category=${cat.categoryId}`)}
              className="flex-1 font-medium text-left text-gray-700"
            >
              {cat.categoryName}
            </button>
            <div className="flex items-center space-x-2">
              {/* 삭제 버튼: 작성자와 현재 사용자가 일치할 때 표시 */}
              {console.log(cat.creatorId)}
              {auth.isAuthenticated && String(auth.userId) === String(cat.creatorId) && (
                <button
                  onClick={() => handleDeleteCategory(cat.categoryId)}
                  className="px-3 py-1 text-xs text-white transition bg-red-500 rounded hover:bg-red-600"
                >
                  삭제
                </button>
              )}
              {/* 즐겨찾기 버튼 */}
              {auth.isAuthenticated && (
                <button
                  onClick={() => handleFavoriteToggle(cat.categoryId)}
                  className="px-3 py-1 text-xs text-white transition rounded"
                  style={{
                    backgroundColor: favorites.includes(cat.categoryId) ? "#FBBF24" : "#4B5563",
                  }}
                >
                  {favorites.includes(cat.categoryId) ? "즐찾해제" : "즐찾추가"}
                </button>
              )}
            </div>
          </li>
        ))}
      </ul>

      {/* 더보기 버튼 */}
      {hasMore && (
        <div className="mt-6 text-center">
          <button
            onClick={handleMore}
            className="px-6 py-2 text-sm font-medium text-white transition bg-blue-600 rounded hover:bg-blue-700"
          >
            더보기
          </button>
        </div>
      )}
    </div>
  );
};

export default CategoryList;
