import React, { Component } from 'react';
import AuthService from '../../actions/AuthService.js';
import axios from 'axios';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import { Alert, Container, Row, Col } from 'react-bootstrap';

import './AdminWorker.css';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';

let BASE_URL = '';
if (process.env.REACT_APP_PROD !== 'true') {
	BASE_URL = 'http://localhost:8080';
}

const API_WORKERS_URL = BASE_URL + '/api/workers';

export default class AdminWorker extends Component {
	constructor() {
		super();

		this.state = {
			workers: [],
			username: '',
			email: '',
			name: '',
			password: '',
			address: '',
			phoneNumber: '',
			selectedWorker: 0,
			availabilities: [],
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
			phoneNumber: this.state.phoneNumber,
			role: [ 'worker' ],
		};

		AuthService.registerWorker(newAccount);
	}

	componentDidMount() {
		// get all the workers
		axios.get(API_WORKERS_URL).then((response) => response.data).then(
			(data) => {
				this.setState({ workers: data });
				console.log(data);
			},
			(error) => {}
		);
	}

	handleRowClick = (row) => {
		console.log(row);
		this.setState({ selectedWorker: row.id });

		axios
			.get(API_WORKERS_URL + `/${row.id}/availability`)
			.then((response) => response.data)
			.then((data) => {
				this.setState({ availabilities: data });
			})
			.catch((error) => {});
	};

	expandComponent = (row) => {
		console.log(this.state.availabilities);

		return (
			<BootstrapTable data={this.state.availabilities}>
				<TableHeaderColumn dataField="weekDay" dataSort={true}>
					Week Day
				</TableHeaderColumn>
				<TableHeaderColumn dataField="startTime" dataSort={true}>
					Start Time
				</TableHeaderColumn>
				<TableHeaderColumn dataField="endTime" dataSort={true}>
					End Time
				</TableHeaderColumn>
				<TableHeaderColumn dataField="id" isKey hidden />
			</BootstrapTable>
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
				JSON.parse(sessionStorage.getItem('user')).roles.includes('ROLE_ADMIN', 0)
			)
		) {
			return (
				<div id="main">
					<h4 className="display-5 text-center">Only admins can access this page!</h4>
				</div>
			);
		}

		// display main page
		return (
			<Container>
				<Row>
					<Col md={{ span: 6, offset: 3 }}>
						{/* displays a message */}
						<Alert variant="danger" id="status" style={{ display: 'none' }}>
							<h6 id="message" style={{ margin: '0px' }} />
						</Alert>
						{/* displays a message */}
						<h2 className="display-5 text-center">Add a worker</h2>
						<div id="addWorker">
							<form onSubmit={this.onSubmit}>
								<div className="form-group">
									<input
										required
										type="text"
										className="form-control form-control-lg "
										placeholder="Username of worker"
										name="username"
										value={this.state.username}
										onChange={this.onChange}
									/>
								</div>

								<div className="form-group">
									<input
										required
										type="password"
										className="form-control form-control-lg"
										placeholder="Password of worker"
										name="password"
										value={this.state.password}
										onChange={this.onChange}
									/>
								</div>

								<div className="form-group">
									<input
										required
										type="email"
										className="form-control form-control-lg "
										placeholder="E-mail of worker"
										name="email"
										value={this.state.email}
										onChange={this.onChange}
									/>
								</div>

								<div className="form-group">
									<input
										required
										type="text"
										className="form-control form-control-lg "
										placeholder="Full Name of worker"
										name="name"
										value={this.state.name}
										onChange={this.onChange}
									/>
								</div>

								<div className="form-group">
									<input
										required
										type="text"
										className="form-control form-control-lg "
										placeholder="Address of worker"
										name="address"
										value={this.state.address}
										onChange={this.onChange}
									/>
								</div>

								<div className="form-group">
									<input
										required
										type="tel"
										className="form-control form-control-lg "
										placeholder="Phone number of worker (04-- --- ---)"
										name="phoneNumber"
										pattern="^(?:\+?(61))? ?(?:\((?=.*\)))?(0?[2-57-8])\)? ?(\d\d(?:[- ](?=\d{3})|(?!\d\d[- ]?\d[- ]))\d\d[- ]?\d[- ]?\d{3})$"
										value={this.state.phoneNumber}
										onChange={this.onChange}
									/>
								</div>
								<input type="submit" value="Add Worker" className="btn btn-primary btn-block mt-4" />
							</form>
						</div>
					</Col>
				</Row>
				<Row>
					<Col md={{ span: 10, offset: 1 }}>
						<h2 className="display-5 text-center">All Workers</h2>
						<h6 className="text-center">Click on an entry to view a worker's working hours</h6>
						<BootstrapTable
							options={options}
							expandColumnOptions={{ expandColumnVisible: true, expandColumnBeforeSelectColumn: false }}
							expandComponent={this.expandComponent}
							expandableRow={() => {
								return true;
							}}
							data={this.state.workers}
							containerStyle={{ background: '#fff' }}
							hover={true}
							bordered={false}
							condensed={true}
						>
							<TableHeaderColumn dataField="username" dataAlign="start">
								Username
							</TableHeaderColumn>
							<TableHeaderColumn dataField="name" dataAlign="start">
								Name
							</TableHeaderColumn>
							<TableHeaderColumn dataField="email" dataAlign="start">
								Email
							</TableHeaderColumn>
							<TableHeaderColumn dataField="address" dataAlign="start" width="25%">
								Address
							</TableHeaderColumn>
							<TableHeaderColumn dataField="phoneNumber" dataAlign="start">
								Phone Number
							</TableHeaderColumn>
							<TableHeaderColumn isKey dataField="id" hidden={true}>
								User ID
							</TableHeaderColumn>
						</BootstrapTable>
					</Col>
				</Row>
			</Container>
		);
	}
}
