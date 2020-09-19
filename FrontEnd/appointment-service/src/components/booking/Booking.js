import React, { Component } from 'react'
import "bootstrap/dist/css/bootstrap.min.css"
import './Booking.css'
import axios from "axios";
import BookingCard from "./BookingCard";
import DatePicker from 'react-date-picker';


const API_BOOKING_URL = "http://localhost:8080/api/bookings?past=false&current=true&customer=";

export default class Booking extends Component {
    constructor() {
        super();

        this.state = {
            bookings: [],
            serviceName: "",
            bookingDateTime: new Date()
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    handleDateChange = date => {
        console.log(date);
        this.setState({
            bookingDateTime: date
        });
    };


    onSubmit(e) {
        e.preventDefault();

        const newBooking = {
            serviceName: this.state.serviceName,
            bookingDateTime: this.bookingDateTime
        }

        // make booking in actions

    }

    componentDidMount() {
        if (!sessionStorage.getItem("user")) {
            return;
        }

        let username = JSON.parse(sessionStorage.getItem("user")).username;
        axios.get(API_BOOKING_URL + username)
            .then(response => response.data)
            .then(data => {
                // display earlier bookings first
                // have to create date objects because they're in string format
                data.sort((a, b) => new Date(a.bookingDateTime) - new Date(b.bookingDateTime));
                this.setState({ bookings: data });
            }, (error) => {
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
                <h2 className="display-5 text-center">Bookings</h2>
                <h6 className="display-5 text-center">On this page, you can view all
                your upcoming bookings and also create new bookings</h6>

                {/* add a booking */}
                <div class="boxContent" id = "leftBox">
                    <h4>None</h4>
                </div>

                {/* view bookings */}
                <div class="boxContent" id = "rightBox">
                    <h4 style={{ color: "white" }}>Your upcoming bookings</h4>
                    <div id="bookingCardArea">
                        {this.state.bookings.length === 0 ?
                            <h4>None</h4>
                            :
                            // loop to generate booking card components
                            this.state.bookings.map((booking) => (
                                <BookingCard booking={booking} />
                            ))

                        }
                    </div>
                </div>

                <p id="bottomText">* We only offer services for <b>Dan's Dentist</b> and <b>Bob's Bowling Ally</b> at this moment</p>
            </div>
        )
    }
}