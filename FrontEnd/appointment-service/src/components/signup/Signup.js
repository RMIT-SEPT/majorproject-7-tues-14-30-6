import React, { Component } from 'react'
import AuthService from "../../actions/AuthService";
import Alert from 'react-bootstrap/Alert'


export default class Signup extends Component {
    constructor() {
        super();

        this.state = {
            username: "",
            email: "",
            password: "",
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
            password: this.state.password,
            name: this.state.name,
            address: this.state.address,
            phoneNumber: this.state.phoneNumber
        }
        
        AuthService.register(newAccount);
    }

    render() {
        return (
            <div className="Person" >
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">

                            {/* displays a message */}
                            <Alert variant="secondary" id="status" style={{ display: "none" }}>
                                <h6 id="message" style={{ margin: "0px" }}></h6>
                            </Alert>
                            {/* displays a message */}

                            <h5 className="display-4 text-center">Register an account</h5>
                            <hr />
                            <form onSubmit={this.onSubmit}>

                                <div className="form-group">
                                    <input type="text" className="form-control form-control-lg "
                                        placeholder="Username"
                                        name="username"
                                        value={this.state.username}
                                        onChange={this.onChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <input type="email" className="form-control form-control-lg "
                                        placeholder="E-mail"
                                        name="email"
                                        value={this.state.email}
                                        onChange={this.onChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <input type="password" className="form-control form-control-lg"
                                        placeholder="Password"
                                        name="password"
                                        value={this.state.password}
                                        onChange={this.onChange}
                                    />
                                </div>


                                <div className="form-group">
                                    <input type="text" className="form-control form-control-lg "
                                        placeholder="Full Name"
                                        name="name"
                                        value={this.state.name}
                                        onChange={this.onChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <input type="text" className="form-control form-control-lg "
                                        placeholder="Address"
                                        name="address"
                                        value={this.state.address}
                                        onChange={this.onChange}
                                    />
                                </div>

                                <div className="form-group">
                                    <input type="tel" className="form-control form-control-lg "
                                        placeholder="Phone number"
                                        name="phoneNumber"
                                        pattern="^(?:\+?(61))? ?(?:\((?=.*\)))?(0?[2-57-8])\)? ?(\d\d(?:[- ](?=\d{3})|(?!\d\d[- ]?\d[- ]))\d\d[- ]?\d[- ]?\d{3})$"
                                        value={this.state.phoneNumber}
                                        onChange={this.onChange}
                                    />
                                </div>

                                <input type="submit" value="Register" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div >
        )
    }
}

