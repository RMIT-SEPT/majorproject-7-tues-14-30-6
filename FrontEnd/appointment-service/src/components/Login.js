import React, { Component } from 'react'

export default class Login extends Component {

    constructor() {
        super();

        this.state = {
            username: "",
            password: ""
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }
    onSubmit(e) {
        e.preventDefault();
        const login = {
            username: this.state.username,
            password: this.state.password
        }

        // Open the console and view the submitted information; good for debugging
        console.log(login);
    }
    render() {
        return (
            <div className="Person">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Log in</h5>
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
                                <input type="submit" value = "Log in" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
