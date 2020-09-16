import React, { Component } from 'react'
import "bootstrap/dist/css/bootstrap.min.css"
import './Booking.css'
import axios from "axios";

export default class Booking extends Component {


    constructor() {
        super();

        this.state = {
            users: []
        };
    }

    // get the users before the page is loaded
    componentDidMount() {
        axios.get('http://localhost:8080/api/users')
            .then(response => response.data)
            .then(data => {
                this.setState({ users: data });
            });
    }


    render() {

        // display message if user is not logged in
        if (!sessionStorage.getItem("user")) {
            return (
                <div id="main">
                    <h4 className="display-5 text-center">Only logged in users can view/make a booking!</h4>
                </div>
            )
        }

        // else display the standard page
        return (
                <div id="main">
                    <div id="viewBookings">
                        <h5 className="display-5 text-center">Add a booking</h5>
                        <hr style={{ margin: "3px" }}></hr>
                    </div>
                    <h2 className="display-5 text-center">Bookings</h2>
                    <h6 className="display-5 text-center">On this page, you can view all
                your upcoming bookings and also create new bookings</h6>
                </div>

        )
    }
}