import { useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {

    const [formData, setFormData] = useState({
      userEmail : "",
      userPw : ""
    });
    const [msg, setMsg] = useState("");
    const { login } = useContext(AuthContext);
    const navi = useNavigate();
    const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    };
    const handleLogin = (e) => {
      e.preventDefault();
      const { userEmail, userPw} = formData;
      const emailRegex3 = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+(?:\.[A-Za-z]{2,})?$/;
      if (!emailRegex3.test(userEmail)){
          setMsg("이메일 값이 유효하지 않습니다.");
          return;
      }
  
      axios.post('/auth/login', formData).then((result) => {
          // console.log(result);
          if(result.status === 200){
            alert("로그인 성공")
      
            login(result.data);
            setTimeout(()=>{
                navi("/");
            }, 1000)
        }
      }).catch((error) => {
          console.log(error);
          alert(error.response.data["error-message"]);
      });
    };

  return (
    <div className="flex items-center justify-center min-h-screen py-8 bg-gray-50">
      <div className="w-full max-w-md p-6 bg-white rounded-lg shadow-md">
        <h2 className="mb-4 text-2xl font-bold text-center text-gray-800">로그인</h2>

        {msg && (
          <p className="mb-4 text-sm text-center text-red-500">
            {msg}
          </p>
        )}

        <form onSubmit={handleLogin} className="space-y-4">
          <input
            name="userEmail"
            type="email"
            placeholder="이메일을 입력해주세요."
            value={formData.userEmail}
            onChange={handleChange}
            required
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />

          <input
            name="userPw"
            type="password"
            placeholder="비밀번호를 입력해주세요."
            value={formData.userPw}
            onChange={handleChange}
            required
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />

          <button
            type="submit"
            className="w-full py-2 font-semibold text-white transition bg-blue-500 rounded hover:bg-blue-600"
          >
            즐거운 로그인하기
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
