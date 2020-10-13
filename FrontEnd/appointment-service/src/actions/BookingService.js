import axios from "axios";

let BASE_URL = "";
if (process.env.REACT_APP_PROD !== "true") {
  BASE_URL = "http://localhost:8080";
}

const BOOKING_URL = BASE_URL + "/api/bookings/add";

const book = (newBooking) => {
  let user = JSON.parse(sessionStorage.getItem("user"));
  let tokenType = user.tokenType;
  let accessToken = user.accessToken;
  let authorization = tokenType + " " + accessToken;

  axios
    .post(BOOKING_URL, JSON.parse(JSON.stringify(newBooking)), {
      headers: {
        Authorization: authorization,
      },
    })
    .then(
      (response) => {
        window.location.href = "/booking";
      },
      (error) => {
        // display error message that booking failed
      }
    );
};

export default {
  book,
};
