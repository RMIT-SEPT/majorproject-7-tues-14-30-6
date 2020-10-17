import React, { Component } from 'react';
import axios from 'axios';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import { Container, Row, Col } from 'react-bootstrap';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';

const API_URL = 'http://localhost:8080';

export default class WorkerBooking extends Component {
	constructor() {
		super();

		this.state = {
			bookings: [],
		};
	}

	componentDidMount() {
		if (!sessionStorage.getItem('user')) {
			return;
		}
		
		let userId = JSON.parse(sessionStorage.getItem('user')).id;

		// get all bookings assigned to the worker
		axios.get(API_URL + `/api/workers/${userId}/bookings`).then((response) => response.data).then(
			(data) => {
				// display earlier bookings first
				// have to create date objects because they're in string format
				data.sort((a, b) => new Date(a.bookingDateTime) - new Date(b.bookingDateTime));
				this.setState({ bookings: data });
			},
			(error) => {}
		);
	}

	render() {
		if (
			!(
				sessionStorage.getItem('user') &&
				JSON.parse(sessionStorage.getItem('user')).roles.includes('ROLE_WORKER', 0)
			)
		) {
			return (
				<div id="main">
					<h4 className="display-5 text-center">Only workers can access this page!</h4>
				</div>
			);
		}

		return (
			<Container>
				<Row xs={2} md={6}>
					<Col md={{ span: 6, offset: 3 }}>
						<h2 className="display-5 text-center">Your Upcoming Bookings</h2>
					</Col>
				</Row>
				<Row>
					<Col md={{ span: 10, offset: 1 }}>
						<BootstrapTable
							containerStyle={{ background: '#fff' }}
							data={this.state.bookings}
							hover={true}
							bordered={false}
							condensed={true}
						>
							<TableHeaderColumn dataField="bookingDateTime" dataSort={true}>Booking Date</TableHeaderColumn>
							<TableHeaderColumn dataField="serviceName">Service Name</TableHeaderColumn>
							<TableHeaderColumn dataField="customerName" dataSort={true}>Customer</TableHeaderColumn>
							<TableHeaderColumn isKey dataField="id" hidden={true} />
						</BootstrapTable>
					</Col>
				</Row>
				<Row />
			</Container>
		);
	}
}
