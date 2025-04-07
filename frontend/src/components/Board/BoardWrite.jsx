// src/components/Board/BoardWrite.jsx
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

const BoardWrite = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [searchParams] = useSearchParams();
  const { auth } = useContext(AuthContext);

  const [boardTitle, setBoardTitle] = useState("");
  const [boardContent, setBoardContent] = useState("");
  const [file, setFile] = useState(null);
  const [preview, setPreview] = useState(null);
  const [accessToken, setAccessToken] = useState("");
  const [memberName, setMemberName] = useState("");

  // 로그인 확인 & 초기값 세팅
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    const name = localStorage.getItem("memberName");
    if (!token) {
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }
    setAccessToken(token);
    setMemberName(name);
  }, [navigate]);

  // 수정 모드: 기존 데이터 로드
  useEffect(() => {
    if (!id) return;
  
    axios
      .get(`/boards/${id}`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => {
        const post = res.data;
  
        // 자기 글인지 체크 (현재 로그인한 사용자의 username과 게시글 작성자(boardWriter) 비교)
        if (String(post.boardWriter) !== String(auth.userId)) {
          alert("자신의 글만 수정할 수 있습니다.");
          navigate(`/board?category=${post.boardCategory}`); // 이전 페이지로 이동하거나 원하는 경로로 리다이렉트
          return;
        }
  
        setBoardTitle(post.boardTitle);
        setBoardContent(post.boardContent);
        if (post.boardFileUrl) setPreview(post.boardFileUrl);
      })
      .catch((err) => console.error("상세 로드 실패:", err));
  }, [id, accessToken, auth, navigate]);

  const handleFileChange = (e) => {
    const selected = e.target.files[0];
    if (!selected) {
      setFile(null);
      setPreview(null);
      return;
    }
    const allowed = ["image/jpg", "image/jpeg", "image/png", "image/gif"];
    const maxSize = 10 * 1024 * 1024;
    if (!allowed.includes(selected.type)) {
      alert("이미지 파일만 업로드 가능합니다.");
      return;
    }
    if (selected.size > maxSize) {
      alert("파일 용량은 10MB 이하만 가능합니다.");
      return;
    }
    setFile(selected);
    setPreview(URL.createObjectURL(selected));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!boardTitle.trim() || !boardContent.trim()) {
      alert("제목과 내용을 모두 입력해주세요.");
      return;
    }

    const formData = new FormData();
    formData.append("boardTitle", boardTitle);
    formData.append("boardContent", boardContent);
    formData.append("boardWriter", auth.userId);
    formData.append("boardCategory", searchParams.get("category"));
    if (file) formData.append("file", file);

    try {
      if (id) {
        await axios.put(`/boards/${id}`, formData, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
        alert("게시글이 수정되었습니다.");
      } else {
        await axios.post("/boards", formData, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
        alert("게시글이 등록되었습니다.");
      }
      navigate(`/board?category=${searchParams.get("category")}`);
    } catch (err) {
      console.error("저장 중 오류 발생:", err);
      alert(err.response?.data["error-message"] || "오류가 발생했습니다.");
    }
  };

  return (
    <div className="max-w-3xl p-6 mx-auto mt-12 bg-white rounded-lg shadow">
      <h2 className="mb-6 text-2xl font-bold text-gray-800">
        {id ? "게시글 수정" : "새 글 작성"}
      </h2>

      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label className="block mb-2 font-semibold text-gray-700">제목</label>
          <input
            type="text"
            placeholder="제목을 입력하세요"
            value={boardTitle}
            onChange={(e) => setBoardTitle(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label className="block mb-2 font-semibold text-gray-700">내용</label>
          <textarea
            rows="6"
            placeholder="내용을 입력하세요"
            value={boardContent}
            onChange={(e) => setBoardContent(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label className="block mb-2 font-semibold text-gray-700">
            작성자
          </label>
          <input
            type="text"
            value={memberName || ""}
            readOnly
            className="w-full p-2 bg-gray-100 border border-gray-300 rounded cursor-not-allowed"
          />
        </div>

        <div>
          <label className="block mb-2 font-semibold text-gray-700">
            이미지 첨부
          </label>
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className="block w-full text-sm text-gray-700"
          />
          {preview && (
            <div className="mt-4">
              <img
                src={preview}
                alt="미리보기"
                className="rounded shadow max-h-48"
              />
            </div>
          )}
        </div>

        <div className="flex justify-end space-x-4">
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
            {id ? "수정하기" : "등록하기"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default BoardWrite;
