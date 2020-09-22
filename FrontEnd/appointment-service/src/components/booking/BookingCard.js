import React, { Component } from 'react'
import "./BookingCard.css"

export default class BookingCard extends Component {
    render() {
        let options = {  
            weekday: "long", year: "numeric", month: "short",  
            day: "numeric", hour: "2-digit", minute: "2-digit"  
        };  
        return (
            <div id="bookingCard" >
                <h5><b>{this.props.booking.serviceName}</b></h5>
                <h5 style={{ color: "grey" }}><b>At </b> {
                    new Date(this.props.booking.bookingDateTime).toLocaleTimeString("en-us", options) 
                }</h5>
            </div>
        )
    }
}
