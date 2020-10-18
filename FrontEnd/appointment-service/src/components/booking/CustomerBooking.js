import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './CustomerBooking.css';
import axios from 'axios';
import BookingCard from './BookingCard';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';
import BookingService from '../../actions/BookingService';

const API_BOOKING_URL = 'http://localhost:8080/api/bookings';
const API_USERS_URL = 'http://localhost:8080/api/users';

export default class CustomerBooking extends Component {
	constructor() {
		super();

		this.state = {
			bookings: [],
			pastBookings: [],
			availableBookings: [],
			bookingId: 0,
			workerDetails: {},
		};
	}

	handleBookClick = (e) => {
		e.preventDefault();

		const data = {
			bookingId: this.state.bookingId,
		};

		BookingService.book(data);
	};

	componentDidMount() {
		if (!sessionStorage.getItem('user')) {
			return;
		}

		// get all the user's bookings
		let username = JSON.parse(sessionStorage.getItem('user')).username;
		axios.get(API_BOOKING_URL + `?past=false&customer=${username}`).then((response) => response.data).then(
			(data) => {
				// display earlier bookings first
				// have to create date objects because they're in string format
				data.sort((a, b) => new Date(a.bookingDateTime) - new Date(b.bookingDateTime));
				this.setState({ bookings: data });
			},
			(error) => {}
		);

		axios.get(API_BOOKING_URL + `?current=false&customer=${username}`).then((response) => response.data).then(
			(data) => {
				this.setState({ pastBookings: data });
			},
			(error) => {}
		);

		axios.get(API_BOOKING_URL + `/available`).then((response) => response.data).then(
			(data) => {
				this.setState({ availableBookings: data });
			},
			(error) => {}
		);
	}

	handleRowClick = (row) => {
		this.setState({ bookingId: row.id });

		axios.get(API_USERS_URL + `/find?id=${row.worker}`).then((response) => response.data).then(
			(data) => {
				this.setState({ workerDetails: data });
			},
			(error) => {}
		);
	};

	expandComponent = (row) => {
		return (
			<Container>
				<Row>Worker's email: {this.state.workerDetails.email}</Row>
				<Row>Worker's phone number: {this.state.workerDetails.phoneNumber}</Row>
				<Row className="mt-2">
					<Form inline onSubmit={this.handleBookClick}>
						<Button variant="primary" type="submit">
							Book
						</Button>
					</Form>
				</Row>
			</Container>
		);
	};

	render() {
		// display message if user is not logged in
		if (!sessionStorage.getItem('user')) {
			return (
				<div id="main">
					<h4 className="display-5 text-center">Only logged in users can access this page!</h4>
				</div>
			);
		}

		const options = {
			expandRowBgColor: 'rgb(242, 255, 163)',
			onlyOneExpanding: true,
			onRowClick: this.handleRowClick,
		};

		// else display the standard page
		return (
			<Container>
				<Row>
					<Col md={{ span: 8, offset: 2 }}>
						<h2 className="display-5 text-center">Add Booking</h2>
						<small>
							* We only offer services for <b>Dan's Dentist</b> at this moment
						</small>
						{/* add a booking */}
						<BootstrapTable
							containerStyle={{ background: '#fff' }}
							options={options}
							expandColumnOptions={{ expandColumnVisible: true, expandColumnBeforeSelectColumn: false }}
							data={this.state.availableBookings}
							hover={true}
							bordered={false}
							condensed={true}
							expandableRow={() => {
								return true;
							}}
							expandComponent={this.expandComponent}
						>
							<TableHeaderColumn dataField="bookingDateTime" dataSort={true}>Booking Date</TableHeaderColumn>
							<TableHeaderColumn dataField="serviceName">Service Name</TableHeaderColumn>
							<TableHeaderColumn dataField="workerName" dataSort={true}>Worker</TableHeaderColumn>
							<TableHeaderColumn isKey dataField="id" hidden={true} />
						</BootstrapTable>
					</Col>
				</Row>
				<Row>
					<Col className="boxContent mr-5">
						<h4 className="mb-3">Your upcoming bookings</h4>
						{this.state.bookings.length === 0 ? (
							<div id="bookingCardArea">
								<h5>No upcoming bookings</h5>
							</div>
						) : (
							// loop to generate booking card components
							this.state.bookings.map((booking) => <BookingCard booking={booking} key={booking.id} />)
						)}
					</Col>
					<Col className="boxContent">
						<h4 className="mb-3">Your past bookings</h4>
						{this.state.pastBookings.length === 0 ? (
							<div id="bookingCardArea">
								<h5>No past bookings</h5>
							</div>
						) : (
							// loop to generate booking card components
							this.state.pastBookings.map((booking) => <BookingCard booking={booking} key={booking.id} />)
						)}
					</Col>
				</Row>
			</Container>
		);
	}
}
