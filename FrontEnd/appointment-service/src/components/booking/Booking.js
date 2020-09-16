import React, { Component } from 'react'
import "bootstrap/dist/css/bootstrap.min.css"
import './Booking.css'
import axios from "axios";

export default class Booking extends Component {


 


    render() {
        var users = [];
        // prints all users. Just for testing.
        // use async function. Check the bookmarks
        axios.get('http://localhost:8080/api/users')
            .then(response => response.data)
            .then(data => {
                users = data;
                console.log(data[0])
            });
        // .then() waits for the function to finish
        // need to wait for the data to fetch first ten render the page
        console.log(users[0]);


        if (!localStorage.getItem("user")) {
            return (
                <h4 className="display-5 text-center">Only logged in users can view/make a booking!</h4>
            )
        }
        return (
            <div id="main">
                <h2 className="display-5 text-center">Bookings</h2>
                <h5 className="display-5 text-center">On this page, you can view all
                your upcoming bookings and also create new bookings</h5>

            </div>

        )
    }
}