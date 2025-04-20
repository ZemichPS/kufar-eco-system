import { saveJwtToken } from "./tokenService";

export const login = (loginRequest) => {
  fetch("http://localhost:8012/api/v1/auth", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(loginRequest),
  })
    .then((res) => res.json())
    .then((data) => {
      if (data.token) {
        saveJwtToken(data.token);
        console.log("token сохранён");
        console.log(localStorage.getItem("jwt_token"));
      } else {
        console.error("token не получен");
      }
    })
    .catch((error) => console.error("Ошибка аутентификации:", error));
};

export const loginWithResponse = async (loginRequest) => {
  try {
    const response = await fetch("http://localhost:8012/api/v1/auth", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(loginRequest),
    });


    if(response.status == 401){
      throw new Error("Неверный email или пароль");
    }

    const data = await response.json();

    if (response.ok && data.token) {
      saveJwtToken(data.token);
      console.log("token сохранён");
      return data;
    } else {
      throw new Error("Something went wrong...");
    }
  } catch (error) {
    throw new Error(error.message || "Ошибка авторизации");
  }
};