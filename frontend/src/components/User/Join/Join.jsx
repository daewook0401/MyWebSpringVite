import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Join = () => {
  const [userEmail, setUserEmail] = useState("");
  const [userPw, setUserPw] = useState("");
  const [userName, setUserName] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navi = useNavigate();

  const handleInputEmail = e => setUserEmail(e.target.value);
  const handleInputPw    = e => setUserPw(e.target.value);
  const handleInputName  = e => setUserName(e.target.value);

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('/users',{
        userEmail,
        userPw,
        userName
    }).then((result)=>{
        console.log(result);
        if(result.status === 201){
            alert('회원가입에 성공하셨습니다.');
            setTimeout(()=>{
                navi("/");
            })
        }
    }).catch((error) => {
        console.log(error);
        setErrorMsg(error.response.data.userEmail);
    });
  };

  return (
    <div className="flex items-center justify-center min-h-screen py-8 bg-gray-50">
      <div className="w-full max-w-md p-6 bg-white rounded-lg shadow-md">
        <h2 className="mb-4 text-2xl font-bold text-center text-gray-800">
          회원가입
        </h2>
        {errorMsg && (
          <p className="mb-4 text-center text-red-500">
            {errorMsg}
          </p>
        )}
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="email"
            placeholder="이메일을 입력해주세요."
            value={userEmail}
            onChange={handleInputEmail}
            required
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
          <input
            type="password"
            placeholder="비밀번호를 입력해주세요."
            value={userPw}
            onChange={handleInputPw}
            required
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
          <input
            type="text"
            placeholder="이름을 입력해주세요."
            value={userName}
            onChange={handleInputName}
            required
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
          <button
            type="submit"
            className="w-full p-2 font-semibold text-white transition bg-blue-500 rounded hover:bg-blue-600"
          >
            즐거운 회원가입 완료하기
          </button>
        </form>
      </div>
    </div>
  );
};

export default Join;
