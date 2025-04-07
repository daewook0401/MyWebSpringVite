import { useState, useEffect, createContext } from "react";

export const AuthContext = createContext();


export const AuthProvider = ({children}) => {
    // session.setAttribute("키", 밸류)
    const [auth, setAuth] = useState({
        userId : null,
        userName : null,
        userEmail : null,
        accessToken : null,
        refreshToken: null,
        isAuthenticated : false,
    });

    useEffect(() => {
        const accessToken = localStorage.getItem("accessToken");
        const refreshToken = localStorage.getItem("refreshToken");
        const userName = localStorage.getItem("userName");
        const userEmail = localStorage.getItem("userEmail");
        const userId = localStorage.getItem("userId");

        if(accessToken && refreshToken && userName && userEmail && userId){
            setAuth({
                userId,
                userEmail,
                userName,
                accessToken,
                refreshToken,
                isAuthenticated: true,
            });
        }
    }, []);

    const login = ({userEmail, userName, userId, accessToken, refreshToken})=>{
        setAuth({
            userId,
            userEmail,
            userName,
            accessToken,
            refreshToken,
            isAuthenticated: true,
        });
        localStorage.setItem("userEmail", userEmail);
        localStorage.setItem("userName", userName);
        localStorage.setItem("userId", userId);
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
    };

    const logout = () => {
        setAuth({
            userId : null,
            userEmail : null,
            userName : null,
            accessToken : null,
            refreshToken : null,
            isAuthenticated: false,
        });
        localStorage.removeItem("userEmail");
        localStorage.removeItem("userName");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("userId");
        window.location.href = "/";
    }
    return(
        <AuthContext.Provider value={{auth, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
};