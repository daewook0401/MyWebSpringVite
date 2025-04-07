// src/components/Category/CategoryCreate.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const CategoryCreate = () => {
  const [categoryName, setCategoryName] = useState("");
  const [error, setError] = useState("");
  const [userName, setUserName] = useState("");
  const [accessToken, setAccessToken] = useState("");
  const navigate = useNavigate();

  useEffect(()=>{
    if(!localStorage.getItem("accessToken")){
      alert('accessTokenIsNull');
      navi("/login");
    } else {
      setUserName(localStorage.getItem("userName"));
      setAccessToken(localStorage.getItem("accessToken"));
    }
  })
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!categoryName.trim()) {
      setError("카테고리 이름을 입력하세요.");
      return;
    }
    try {
      await axios.post("/categories", { categoryName },  {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      });
      navigate("/categories");
    } catch (err) {
      console.error(err);
      setError(
        err.response?.data["error-message"] || "카테고리 생성 중 오류가 발생했습니다."
      );
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen p-4 bg-gray-50">
      <div className="w-full max-w-md p-6 bg-white rounded-lg shadow-md">
        <h2 className="mb-4 text-2xl font-bold text-center text-gray-800">
          카테고리 추가
        </h2>

        {error && (
          <p className="mb-4 text-sm text-center text-red-500">{error}</p>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="카테고리 이름을 입력해주세요."
            value={categoryName}
            onChange={(e) => setCategoryName(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />

          <div className="flex justify-between">
            <button
              type="button"
              onClick={() => navigate(-1)}
              className="px-4 py-2 text-gray-700 transition bg-gray-200 rounded hover:bg-gray-300"
            >
              취소
            </button>
            <button
              type="submit"
              className="px-4 py-2 text-white transition bg-blue-500 rounded hover:bg-blue-600"
            >
              저장
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CategoryCreate;
