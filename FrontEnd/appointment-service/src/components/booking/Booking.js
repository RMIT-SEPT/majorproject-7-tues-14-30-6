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
        if (this.state.users.length > 0) {
            console.log(this.state.users[0]);
        }

        if (!sessionStorage.getItem("user")) {
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