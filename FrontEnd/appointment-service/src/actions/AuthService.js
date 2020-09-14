import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

// the changes made to the status display are in both clauses.
// faster response times is why.

// register an account
const register = (newAccount) => {
  axios.post(API_URL + "register", JSON.parse(JSON.stringify(newAccount))).then((response) => {
    document.getElementById("message").innerHTML = response.data.message;
    document.getElementById("status").style.display = "block";
  }, (error) => {
    document.getElementById("status").style.display = "block";
    document.getElementById("message").innerHTML = "Error! Failed to sign up!";
  });

}

// login
const login = (account) => {

  axios.post(API_URL + "login", JSON.parse(JSON.stringify(account))).then((response) => {
    document.getElementById("status").style.display = "block";
    document.getElementById("message").innerHTML = "Successfully logged in as " + response.data.username;
  }, (error) => {
    document.getElementById("message").innerHTML = "Failed to log in!";
    document.getElementById("status").style.display = "block";
  });

}


// put the other const in here as an array
export default {
  register,
  login
};