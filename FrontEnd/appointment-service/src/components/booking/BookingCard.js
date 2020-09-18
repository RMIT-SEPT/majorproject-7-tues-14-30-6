import React, { Component } from 'react'

export default class BookingCard extends Component {
    render() {
        return (
            // this will match the min-height of the div
            <div style = {{height: this.props.cardHeight}}>
               <p>{this.props.booking.id}</p>
            </div>
        )
    }
}
