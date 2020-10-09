import React, { Component } from 'react'
import AuthService from '../../actions/AuthService.js'

export default class Worker extends Component {
    constructor() {
        super();

        this.state = {
            username: "",
            email: "",
            name: "",
            address: "",
            phoneNumber: ""
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }
    onSubmit(e) {
        e.preventDefault();

        const newAccount = {
            username: this.state.username,
            email: this.state.email,
            // create a random default password for the worker
            password: Math.random().toString(36).substring(5),
            name: this.state.name,
            address: this.state.address,
            phoneNumber: this.state.phoneNumber
        }

        // AuthService.register(newAccount);
    }


    render() {

        if (!(sessionStorage.getItem("user") && JSON.parse(sessionStorage.getItem("user")).roles.includes("ROLE_ADMIN", 0))) {
            return (
                <div id="main">
                    <h4 className="display-5 text-center">Only admins can use this page to add workers!</h4>
                </div>
            )
        }

        // display main page
        return (
            <div id="main">
                <h2 className="display-5 text-center">Workers</h2>
                <h6 className="display-5 text-center">On this page, you can add workers for clients to book through them</h6>

            </div>
        )
    }
}
