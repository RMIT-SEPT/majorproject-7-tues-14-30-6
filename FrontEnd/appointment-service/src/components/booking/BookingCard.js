import React, { Component } from 'react'
import "./BookingCard.css"

export default class BookingCard extends Component {
    render() {
        return (
            // this height will match the min-height of the div
            // this means we can update it one place
            <div id="bookingCard" >
                <h5><b>{this.props.booking.serviceName}</b></h5>
                <h5 style ={{color: "grey"}}><b>At </b> {this.props.booking.bookingDateTime}</h5>
            </div>
        )
    }
}
