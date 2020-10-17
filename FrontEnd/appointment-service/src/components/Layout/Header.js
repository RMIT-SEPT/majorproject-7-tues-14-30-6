import React, { Component } from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';

class Header extends Component {
	render() {
		return (
			<Navbar expand="lg" bg="primary" variant="dark" className="mb-5">
				<Container>
					<Navbar.Brand href="/">AGME</Navbar.Brand>
					<Navbar.Toggle aria-controls="responsive-navbar-nav" />
					<Navbar.Collapse>
						<Nav className="mr-auto" activeKey={this.props.location.pathname}>
							<Nav.Link href="/">Home</Nav.Link>
							{sessionStorage.getItem('user') &&
							JSON.parse(sessionStorage.getItem('user')).roles.includes('ROLE_CUSTOMER', 0) && (
								<Nav.Link href="/booking">Bookings</Nav.Link>
							)}
							{sessionStorage.getItem('user') &&
							JSON.parse(sessionStorage.getItem('user')).roles.includes('ROLE_ADMIN', 0) && (
								<React.Fragment>
									<Nav.Link href="/admin-booking">Bookings</Nav.Link>
									<Nav.Link href="/worker">Workers</Nav.Link>
								</React.Fragment>
							)}
							{sessionStorage.getItem('user') &&
							JSON.parse(sessionStorage.getItem('user')).roles.includes('ROLE_WORKER', 0) && (
								<React.Fragment>
									<Nav.Link href="/worker-booking">Bookings</Nav.Link>
									<Nav.Link href="/availability">Availability</Nav.Link>
								</React.Fragment>
							)}
						</Nav>

						<Nav>
							{sessionStorage.getItem('user') ? (
								<React.Fragment>
									<Navbar.Text>
										Signed in as: {JSON.parse(sessionStorage.getItem('user')).username}
									</Navbar.Text>
									<Nav.Link href="/login" onClick={() => sessionStorage.clear()}>
										Log out
									</Nav.Link>
								</React.Fragment>
							) : (
								<React.Fragment>
									<Nav.Link href="/login">Login</Nav.Link>
									<Nav.Link href="/signup">Register</Nav.Link>
								</React.Fragment>
							)}
						</Nav>
					</Navbar.Collapse>
				</Container>
			</Navbar>
		);
	}
}
export default withRouter(Header);
