import React, { Component } from 'react'
import "bootstrap/dist/css/bootstrap.min.css"
import './Booking.css'

export default class Booking extends Component {
    render() {
        return (
            <div id = "main">
                <h2 className="display-5 text-center">Bookings</h2>
                <h5 className="display-5 text-center">On this page, you can view all 
                your upcoming bookings and also create new bookings</h5>
            </div>
            // TODO. create a get for all the upcoming bookings
        )
    }
}