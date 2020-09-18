import React, { Component } from 'react'
import "bootstrap/dist/css/bootstrap.min.css"
import './Booking.css'
import axios from "axios";
import BookingCard from "./BookingCard";

const API_BOOKING_URL = "http://localhost:8080/api/bookings?past=false&current=true&customer=";

export default class Booking extends Component {
    constructor() {
        super();

        this.state = {
            bookings: []
        };
    }

    componentDidMount() {
        let username = JSON.parse(sessionStorage.getItem("user")).username;
        axios.get(API_BOOKING_URL + username)
            .then(response => response.data)
            .then(data => {
                this.setState({ bookings: data });
            }, (error) => {
            });

    }


    render() {
        console.log(this.state.bookings);
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
                <div id="addBooking">
                    <h5 className="display-5 text-center">Add a booking*</h5>
                    <hr style={{ margin: "3px" }}></hr>
                </div>
                <h2 className="display-5 text-center">Bookings</h2>
                <h6 className="display-5 text-center">On this page, you can view all
                your upcoming bookings and also create new bookings</h6>

                <div id="viewBookings">
                    <h4>Your upcoming bookings</h4>
                    <div id="bookingCardArea">
                        {this.state.bookings.length === 0 ?
                            <h4>No upcoming bookings</h4>
                            :
                            this.state.bookings.map((booking) => (
                                <BookingCard booking={booking} cardHeight={document.querySelector('#bookingCardArea').clientHeight} />
                            ))

                        }
                        {/* loop here to generate booking cards */}
                    </div>
                </div>

                <p id="bottomText">* We only offer services for <b>Dan's Dentist</b> and <b>Bob's Bowling Ally</b> at this moment</p>
            </div>
        )
    }
}