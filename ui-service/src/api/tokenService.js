export const getJwtToken = () => sessionStorage.getItem("jwt_token");
export const saveJwtToken = (token) => sessionStorage.setItem("jwt_token", token);
export const removeJwtToken = (token) => sessionStorage.removeItem("jwt_token", token);
