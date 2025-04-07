import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom"
const Join = () => {
  const [userEmail, setUserEmail] = useState("");
  const [userPw, setUserPw] = useState("");
  const [userName, setUserName] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navi = useNavigate();
  
  const handleInputEmail = e => {
    setUserEmail(e.target.value);
  };
  const handleInputPw = (e) => {
    setUserEmail(e.target.value);
  };
  const handleInputName = (e) => {
    setUserName(e.target.value);
  }
  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post('http://localhost/users',{
        userEmail,
        userPw,
        userName
    }).then((result)=>{
        console.log(result);
        if(result.status === 201){
            alert('회원가입에 성공하셨습니다.');
            setTimeout(()=>{
                navi("/");
            }, 1000)
        }
    }).catch((error) => {
        console.log(error);
        setErrorMsg(error.response.data.memberId);
    });
  };

  return (
    <>
      <Container>
        <Form onSubmit={handleSubmit}>
          <Title>회원가입</Title>
          <div>{errorMsg}</div>
          <Input
            onChange={handleInputEmail}
            name="userEmail"
            type="text"
            placeholder="이메일을 입력해주세요."
            required
          />
          <Input
            onChange={handleInputPw}
            name="userPw"
            type="password"
            placeholder="비밀번호를 입력해주세요."
            required
          />
          <Input
            onChange={handleInputName}
            name="userName"
            type="text"
            placeholder="이름을 입력해주세요."
            required
          />
          <Button type="submit">즐거운 회원가입 완료하기</Button>
        </Form>
      </Container>
    </>
  );
};
export default Join;