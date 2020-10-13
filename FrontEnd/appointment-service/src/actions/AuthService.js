import axios from "axios";

let BASE_URL = "";
if (process.env.REACT_APP_PROD !== "true") {
  BASE_URL = "http://localhost:8080";
}

const API_URL = BASE_URL + "/api/auth/";

// register an account
const register = (newAccount) => {
  axios.post(API_URL + "register", JSON.parse(JSON.stringify(newAccount))).then(
    (response) => {
      window.location.href = "/login";
    },
    (error) => {
      document.getElementById("status").style.display = "block";
      document.getElementById("message").innerHTML =
        "Error! Failed to sign up!";
    }
  );
};

// login
const login = (account) => {
  axios.post(API_URL + "login", JSON.parse(JSON.stringify(account))).then(
    (response) => {
      sessionStorage.setItem("user", JSON.stringify(response.data));
      window.location.href = "/ ";
    },
    (error) => {
      document.getElementById("message").innerHTML = "Failed to log in!";
      document.getElementById("status").style.display = "block";
    }
  );
};

// put the other const in here as an array
export default {
  register,
  login,
};
