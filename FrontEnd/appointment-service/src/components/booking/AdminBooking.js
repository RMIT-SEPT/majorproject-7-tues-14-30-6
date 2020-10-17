import React, { Component } from 'react';
import axios from 'axios';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import { Form, Container, Row, Col, Button } from 'react-bootstrap';

import BookingService from '../../actions/BookingService';
import AddBookingModal from './AddBookingModal';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';

const API_URL = 'http://localhost:8080';

export default class AdminBooking extends Component {
	constructor() {
		super();

		this.state = {
			bookings: [],
			serviceName: '', // make sure this matches the default/disabled option in the select
			bookingDateTime: '', // don't initalize a date object here else it will set its default value to that
			modalShow: false,
			workers: [],
			workerId: 0,
			bookingId: 0,
		};
	}

	componentDidMount() {
		if (!sessionStorage.getItem('user')) {
			return;
		}

		axios
			.get(API_URL + '/api/bookings?past=true&current=true&cancelled=true')
			.then((response) => response.data)
			.then(
				(data) => {
					// display earlier bookings first
					// have to create date objects because they're in string format
					data.sort((a, b) => new Date(a.bookingDateTime) - new Date(b.bookingDateTime));
					this.setState({ bookings: data });
				},
				(error) => {}
			);
	}

	createSelectItems() {
		return this.state.workers.map((v) => <option value={v.id}>{v.name}</option>);
	}

	handleRowClick = (row) => {
		this.setState({ bookingId: row.id });

		axios
			.get(API_URL + `/api/bookings/${row.id}/availableWorkers`)
			.then((response) => response.data)
			.then((data) => {
				this.setState({ workers: data });
				this.setState({ workerId: data[0].id });
			});
	};

	handleSelectChange = (e) => {
		this.setState({ [e.target.id]: e.target.value });
	};

	handleAssignClick = (e) => {
		e.preventDefault();

		const data = {
			workerId: this.state.workerId,
			bookingId: this.state.bookingId,
		};

		BookingService.assignWorker(data);
	};

	isExpandableRow = (row) => (row.active && row.worker === null ? true : false);

	expandComponent = (row) => {
		return (
			<Form inline onSubmit={this.handleAssignClick}>
				<Form.Group controlId="workerId">
					<Form.Label className="mr-2">Assign A Worker</Form.Label>
					<Form.Control
						className="mr-3"
						as="select"
						size="sm"
						custom
						value={this.state.workerId}
						onChange={this.handleSelectChange}
					>
						{this.createSelectItems()}
					</Form.Control>
				</Form.Group>
				<Button variant="primary" type="submit">
					Assign
				</Button>
			</Form>
		);
	};

	render() {
		const options = {
			expandRowBgColor: 'rgb(242, 255, 163)',
			onlyOneExpanding: true,
			onRowClick: this.handleRowClick,
			sortIndicator: true,
		};
		const bookingStatus = (cell, row) => {
			let statusText = 'Cancelled';
			if (cell === true) {
				statusText = 'Active';
			}
			return statusText;
		};

		if (
			!(
				sessionStorage.getItem('user') &&
				JSON.parse(sessionStorage.getItem('user')).roles.includes('ROLE_ADMIN', 0)
			)
		) {
			return (
				<div id="main">
					<h4 className="display-5 text-center">Only admins can access this page!</h4>
				</div>
			);
		}

		return (
			<Container>
				<Row xs={2} md={6}>
					<Col md={{ span: 5, offset: 3 }}>
						<h2 className="display-5 text-center">Bookings</h2>
						<h6 className="text-center">Click on a booking to assign workers</h6>
					</Col>
					<Col>
						<Button variant="primary" onClick={() => this.setState({ modalShow: true })}>
							Add Booking
						</Button>
						<AddBookingModal
							show={this.state.modalShow}
							onHide={() => this.setState({ modalShow: false })}
						/>
					</Col>
				</Row>
				<Row>
					<Col md={{ span: 10, offset: 1 }}>
						<BootstrapTable
							containerStyle={{ background: '#fff' }}
							options={options}
							expandColumnOptions={{ expandColumnVisible: true, expandColumnBeforeSelectColumn: false }}
							data={this.state.bookings}
							hover={true}
							bordered={false}
							condensed={true}
							expandableRow={this.isExpandableRow}
							expandComponent={this.expandComponent}
						>
							<TableHeaderColumn dataField="bookingDateTime" dataSort={true}>
								Booking Date
							</TableHeaderColumn>
							<TableHeaderColumn dataField="serviceName">Service Name</TableHeaderColumn>
							<TableHeaderColumn dataField="workerName" dataSort={true}>
								Worker
							</TableHeaderColumn>
							<TableHeaderColumn dataField="customerName" dataSort={true}>
								Customer
							</TableHeaderColumn>
							<TableHeaderColumn
								dataField="active"
								dataFormat={bookingStatus}
								width="15%"
								dataSort={true}
							>
								Status
							</TableHeaderColumn>
							<TableHeaderColumn isKey dataField="id" hidden={true} />
						</BootstrapTable>
					</Col>
				</Row>
				<Row />
			</Container>
		);
	}
}
