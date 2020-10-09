import React, { Component } from 'react'
import "bootstrap/dist/css/bootstrap.min.css"
import './Booking.css'
import axios from "axios"
import BookingCard from "./BookingCard"
import DatePicker from 'react-date-picker'
import { Button } from 'react-bootstrap'
import BookingService from '../../actions/BookingService'


const API_BOOKING_URL = "http://localhost:8080/api/bookings?past=false&current=true&customer=";

export default class Booking extends Component {
    constructor() {
        super();

        this.state = {
            bookings: [],
            serviceName: "", // make sure this matches the default/disabled option in the select
            workerName: "",
            bookingDateTime: "" // don't initalize a date object here else it will set its default value to that
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    handleDateChange = date => {
        this.setState({
            bookingDateTime: date
        });
    };


    onSubmit(e) {
        e.preventDefault();
        const newBooking = {
            customerId: JSON.parse(sessionStorage.getItem("user")).id,
            serviceName: this.state.serviceName,
            workerName: this.state.workerName,
            bookingDateTime: this.state.bookingDateTime
        }
        BookingService.book(newBooking);
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

        let currentDate = new Date();
        let nextWeek = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);

        // else display the standard page
        return (
            <div id="main">
                <h2 className="display-5 text-center">Bookings</h2>
                <h6 className="display-5 text-center">On this page, you can view all
                your upcoming bookings and also create new bookings</h6>

                {/* add a booking */}
                <div className="boxContent" id="leftBox">
                    <h4>Add new booking</h4>
                    <hr />

                    {/* start of form  */}
                    <form onSubmit={this.onSubmit}>
                        <div className="form-group">

                            {/* service name */}
                            <label for="serviceName">Select service</label>
                            <select type="text"
                                className="form-control" required
                                name="serviceName"
                                value={this.state.serviceName}
                                onChange={this.onChange}
                            >
                                <option value="" disabled>Please select a service</option>
                                <option value="Dan's dentist">Dan's dentist</option>
                            </select>

                            {/* select a worker */}
                            <label for="workerName">Select Worker</label>
                            <select type="text"
                                className="form-control" required
                                name="workerName"
                                value={this.state.workerName}
                                onChange={this.onChange}
                            >
                                <option value="" disabled>Please select a worker</option>
                                <option value="John Smith">John Smith</option>
                                <option value="Tim Mark">Tim Mark</option>
                            </select>




                            <label for="bookingDateTime">Select a date</label>
                            <DatePicker
                                required
                                name="bookingDateTime"
                                className="form-control"
                                minDate={nextWeek}
                                onChange={this.handleDateChange}
                                value={this.state.bookingDateTime}
                            />
                        </div>
                        <Button type="submit" variant="outline-light" size="lg" block style={{ marginTop: "35px" }}>
                            Book
                         </Button>
                    </form>
                    {/* end of form */}

                </div>

                {/* view bookings */}
                <div className="boxContent" id="rightBox">
                    <h4 >Your upcoming bookings</h4>
                    <hr />
                    {this.state.bookings.length === 0 ?
                        <div id="bookingCardArea">
                            <h5>No upcoming bookings</h5>
                        </div>
                        :
                        // loop to generate booking card components
                        this.state.bookings.map((booking) => (
                            <BookingCard booking={booking} key={booking.id} />
                        ))
                    }

                </div>
                <p id="bottomText">* We only offer services for <b>Dan's Dentist</b> at this moment</p>
            </div >
        )
    }
}