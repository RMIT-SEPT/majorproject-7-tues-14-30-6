import React, { Component } from 'react';
import axios from 'axios';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import { Form, Container, Row, Col, Button } from 'react-bootstrap';
import TimePicker from 'react-time-picker';

import AvailabilityService from '../../actions/AvailabilityService';
import AddAvailabilityModal from './AddAvailabilityModal';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';

let BASE_URL = '';
if (process.env.REACT_APP_PROD !== 'true') {
	BASE_URL = 'http://localhost:8080';
}

const API_URL = BASE_URL + '/api/workers';

export default class Availability extends Component {
	constructor() {
		super();

		this.state = {
			availabilities: [],
			modalShow: false,
			availabilityId: 0,
			weekDay: 'Monday',
			startTime: null,
			endTime: null,
		};
	}

	componentDidMount() {
		if (!sessionStorage.getItem('user')) {
			return;
		}

		let userId = JSON.parse(sessionStorage.getItem('user')).id;

		axios.get(API_URL + `/${userId}/availability`).then((response) => response.data).then(
			(data) => {
				this.setState({ availabilities: data });
			},
			(error) => {}
		);
	}

	handleRowClick = (row) => {
		this.setState({ availabilityId: row.id });
	};

	handleStartTimeChange = (value) => {
		this.setState({ startTime: value });
	};

	handleEndTimeChange = (value) => {
		this.setState({ endTime: value });
	};

	handleSelectedValue = (e) => {
		this.setState({ weekDay: e.target.value });
	};

	handleUpdateClick = (e) => {
		e.preventDefault();

		let userId = JSON.parse(sessionStorage.getItem('user')).id;

		const availabilityRequest = {
			workerId: userId,
			id: this.state.availabilityId,
			data: {
				weekDay: this.state.weekDay,
				startTime: this.state.startTime,
				endTime: this.state.endTime,
			},
		};
		AvailabilityService.updateAvailability(availabilityRequest);
	};

	handleRemoveClick = (e) => {
		e.preventDefault();

		let userId = JSON.parse(sessionStorage.getItem('user')).id;

		const availabilityRequest = {
			workerId: userId,
			id: this.state.availabilityId,
		};
		AvailabilityService.removeAvailability(availabilityRequest);
	};

	expandComponent = (row) => {
		return (
			<React.Fragment>
				<Form onSubmit={this.handleUpdateClick}>
					<Form.Group as={Row} controlId="weekDay">
						<Form.Label column md={2} className="mr-2">
							Week Day
						</Form.Label>
						<Col md={3}>
							<Form.Control as="select" onChange={this.handleSelectedValue}>
								<option>Monday</option>
								<option>Tuesday</option>
								<option>Wednesday</option>
								<option>Thursday</option>
								<option>Friday</option>
								<option>Saturday</option>
								<option>Sunday</option>
							</Form.Control>
						</Col>
					</Form.Group>
					<Form.Row className="mb-2">
						<Form.Group as={Col} controlId="startTime">
							<Form.Label className="mr-2">Start Time (24hr format)</Form.Label>
							<TimePicker
								hourPlaceholder="HH"
								minutePlaceholder="mm"
								required={true}
								name="bookingTime"
								onChange={this.handleStartTimeChange}
								value={this.state.startTime}
								clearIcon={null}
								disableClock={true}
								format="HH:mm"
								locale="en-AU"
							/>
						</Form.Group>
						<Form.Group as={Col} controlId="endTime">
							<Form.Label className="mr-2">End Time (24hr format)</Form.Label>
							<TimePicker
								hourPlaceholder="HH"
								minutePlaceholder="mm"
								required={true}
								name="bookingTime"
								onChange={this.handleEndTimeChange}
								value={this.state.endTime}
								clearIcon={null}
								disableClock={true}
								format="HH:mm"
								locale="en-AU"
							/>
						</Form.Group>
					</Form.Row>
					<Button variant="primary" type="submit">
						Update
					</Button>
				</Form>
				<Form onSubmit={this.handleRemoveClick}>
					<Button variant="danger" type="submit" className="mt-3">
						Remove
					</Button>
				</Form>
			</React.Fragment>
		);
	};

	render() {
		const options = {
			expandRowBgColor: 'rgb(242, 255, 163)',
			onlyOneExpanding: true,
			onRowClick: this.handleRowClick,
		};

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
					<Col md={{ span: 5, offset: 3 }}>
						<h2 className="display-5 text-center">Your Working Hours</h2>
						<h6 className="text-center">Click on an entry to update or remove</h6>
					</Col>
					<Col>
						<Button variant="primary" onClick={() => this.setState({ modalShow: true })}>
							Add Availability
						</Button>
						<AddAvailabilityModal
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
							data={this.state.availabilities}
							hover={true}
							bordered={false}
							condensed={true}
							expandableRow={() => {
								return true;
							}}
							expandComponent={this.expandComponent}
						>
							<TableHeaderColumn dataField="weekDay" dataSort={true}>
								Week Day
							</TableHeaderColumn>
							<TableHeaderColumn dataField="startTime" dataSort={true}>
								Start Time
							</TableHeaderColumn>
							<TableHeaderColumn dataField="endTime" dataSort={true}>
								End Time
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
