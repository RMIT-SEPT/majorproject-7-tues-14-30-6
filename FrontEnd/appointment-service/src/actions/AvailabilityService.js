import axios from 'axios';

let BASE_URL = '';
if (process.env.REACT_APP_PROD !== 'true') {
	BASE_URL = 'http://localhost:8080';
}

const WORKER_URL = BASE_URL + '/api/workers';

const addAvailability = (data) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.post(WORKER_URL + `/${data.workerId}/availability`, JSON.parse(JSON.stringify(data.data)), {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/availability';
			},
			(error) => {}
		);
};

const updateAvailability = (data) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.put(WORKER_URL + `/${data.workerId}/availability/${data.id}`, JSON.parse(JSON.stringify(data.data)), {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/availability';
			},
			(error) => {}
		);
};

const removeAvailability = (data) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.delete(WORKER_URL + `/${data.workerId}/availability/${data.id}`, {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/availability';
			},
			(error) => {}
		);
};

export default {
	addAvailability,
	updateAvailability,
	removeAvailability,
};
