import React, { Component } from 'react'
import doctor from "../images/doctors.png";
import './Home.css'

export default class Home extends Component {
    render() {
        return (
            <div id = "homePage">
                <h1 className="display-5 text-center">Welcome to AGME</h1>
                <h5 className="display-5 text-center">Essential booking service for any bussiness</h5>
                {/* This is from the boot strap classes. 
                <p className="alert alert-warning"> Warning</p>
                */}
                <img src={doctor} id = "doctor" alt="Doctors appointment" />
            </div>
        )
    }
}