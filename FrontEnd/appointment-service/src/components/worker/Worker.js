import React, { Component } from 'react'
import AuthService from '../../actions/AuthService.js'
import './Worker.css'

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
            phoneNumber: this.state.phoneNumber,
            role: ["worker"]
        }

        AuthService.register(newAccount);
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
                <div id="addWorker">
                    <form onSubmit={this.onSubmit}>
                        <div className="form-group">
                            <input type="text" className="form-control form-control-lg "
                                placeholder="Username of worker"
                                name="username"
                                value={this.state.username}
                                onChange={this.onChange}
                            />
                        </div>

                        <div className="form-group">
                            <input type="email" className="form-control form-control-lg "
                                placeholder="E-mail of worker"
                                name="email"
                                value={this.state.email}
                                onChange={this.onChange}
                            />
                        </div>



                        <div className="form-group">
                            <input type="text" className="form-control form-control-lg "
                                placeholder="Full Name of worker"
                                name="name"
                                value={this.state.name}
                                onChange={this.onChange}
                            />
                        </div>

                        <div className="form-group">
                            <input type="text" className="form-control form-control-lg "
                                placeholder="Address of worker"
                                name="address"
                                value={this.state.address}
                                onChange={this.onChange}
                            />
                        </div>

                        <div className="form-group">
                            <input type="tel" className="form-control form-control-lg "
                                placeholder="Phone number of worker"
                                name="phoneNumber"
                                pattern="^(?:\+?(61))? ?(?:\((?=.*\)))?(0?[2-57-8])\)? ?(\d\d(?:[- ](?=\d{3})|(?!\d\d[- ]?\d[- ]))\d\d[- ]?\d[- ]?\d{3})$"
                                value={this.state.phoneNumber}
                                onChange={this.onChange}
                            />
                        </div>
                        <input type="submit" value="Add Worker" className="btn btn-primary btn-block mt-4" />
                    </form>
                </div>
            </div>
        )
    }
}
