import { USER_SERVER_URL } from "../constants";
import { getJwtToken } from "./tokenService";

export const fetchUsers = ()=> 
    fetch(USER_SERVER_URL + "/api/v1/users", {
    headers: {
      Authorization:
        "Bearer " + getJwtToken(),
    },
  })
.then((res) => res.json())
