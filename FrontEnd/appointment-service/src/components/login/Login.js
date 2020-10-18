import React, { Component } from 'react';
import AuthService from '../../actions/AuthService';
import { Alert, Container, Row, Col } from 'react-bootstrap';

export default class Login extends Component {
	constructor() {
		super();

		this.state = {
			username: '',
			password: '',
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
			password: this.state.password,
		};

		// log in function
		AuthService.login(login);
	}
	render() {
		return (
			<Container>
				<Row>
					<Col md={{ span: 6, offset: 3 }}>
						{/* displays a message */}
						<Alert variant="danger" id="status" style={{ display: 'none' }}>
							<h6 id="message" style={{ margin: '0px' }} />
						</Alert>
						{/* displays a message */}

						<h5 className="display-4 text-center">Log in</h5>
						<hr />
						<form onSubmit={this.onSubmit}>
							<div className="form-group">
								<input
									type="text"
									className="form-control form-control-lg "
									required
									placeholder="Username"
									name="username"
									value={this.state.username}
									onChange={this.onChange}
								/>
							</div>
							<div className="form-group">
								<input
									type="password"
									className="form-control form-control-lg"
									required
									placeholder="Password"
									name="password"
									value={this.state.password}
									onChange={this.onChange}
								/>
							</div>
							<input type="submit" value="Log in" className="btn btn-primary btn-block mt-4" />
						</form>
					</Col>
				</Row>
			</Container>
		);
	}
}
