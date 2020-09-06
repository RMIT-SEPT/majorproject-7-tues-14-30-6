import React, { Component } from 'react'

export default class Signup extends Component {
    constructor() {
        super();

        this.state = {
            username: "",
            password: "",
            name: "",
            address: "",
            telephone: ""
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
            password: this.state.password,
            name: this.state.name,
            address: this.state.address,
            telephone: this.state.telephone
        }

        console.log(newAccount);
    }
    render() {
        return (
            <div className="Person">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
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
                                        name="telephone"
                                        pattern="^(?:\+?(61))? ?(?:\((?=.*\)))?(0?[2-57-8])\)? ?(\d\d(?:[- ](?=\d{3})|(?!\d\d[- ]?\d[- ]))\d\d[- ]?\d[- ]?\d{3})$"
                                        value={this.state.telephone}
                                        onChange={this.onChange}
                                    />
                                </div>


                                <input type="submit" value="Register" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
