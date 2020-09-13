import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

// used for registering an account
const register = (newAccount, message) => {
  axios.post(API_URL + "register", JSON.parse(JSON.stringify(newAccount))).then((response) => {
    
    document.getElementById("message").innerHTML = response.data.message;
  }, (error) => {
    document.getElementById("message").innerHTML = "Error! Failed to sign up!";
    console.log(error);
  });
  document.getElementById("status").style.display = "block";
}

// put the other const in here as an array
export default { register };