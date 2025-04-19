import React, { useState } from "react";
import axios from "axios"

function LoginForm(){
    
    const login_url = process.env.REACT_APP_API_LOGIN_URL;
    const [loginRequest, setRequest] = useState({
        email:"",
        password:""
    });

    const inputChanged = (event) => {
        setRequest({...loginRequest, [event.target.name] : event.target.value});
    };

    const handleSubmit =(event)=>{
        event.preventDefault();
        fetch("http://localhost:8012/api/v1/auth", {
            method: "POST",
            headers: {
                "Content-Type":"application/json"
            },
            body: JSON.stringify(loginRequest)
        })
        .then(res => res.json())
        .then(data=>{
            if(data.token){
                localStorage.setItem("token", data.token);
                console.log("token сохранён");
                console.log(localStorage.getItem("token"))
            } else {
                console.error("token не получен")
            }
        })
        .catch(error => console.error("Ошибка аутентификации:", error));
    } 

    return(
        <form onSubmit={(e)=> handleSubmit(e)}>
            <input type="text" placeholder="email" value={loginRequest.email} name = "email" onChange={inputChanged}/> <br/>
            <input type="text" placeholder="password" value={loginRequest.password} name="password" onChange={inputChanged}/> <br/>
            <button type="submit">Sign in</button>
        </form>
    );
}

export default LoginForm;