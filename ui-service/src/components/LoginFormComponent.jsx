import React, { useState } from "react";
import { login } from "../api/aouthService";

function LoginForm() {
  const [loginRequest, setRequest] = useState({
    email: "",
    password: "",
  });

  const inputChanged = (event) => {
    setRequest({ ...loginRequest, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    login(loginRequest);
  };

  return (
    <div
      style={{
        width: "250px",
        height: "100px",
        border: "2px solid orange",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <form onSubmit={(e) => handleSubmit(e)}>
        <input
          type="text"
          placeholder="email"
          value={loginRequest.email}
          name="email"
          onChange={inputChanged}
        />{" "}
        <br />
        <input
          type="text"
          placeholder="password"
          value={loginRequest.password}
          name="password"
          onChange={inputChanged}
        />{" "}
        <br />
        <button type="submit">Sign in</button>
      </form>
    </div>
  );
}

export default LoginForm;
