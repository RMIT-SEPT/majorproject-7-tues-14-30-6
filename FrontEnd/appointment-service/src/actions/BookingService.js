import axios from 'axios';

let BASE_URL = '';
if (process.env.REACT_APP_PROD !== 'true') {
	BASE_URL = 'http://localhost:8080';
}

const BOOKING_URL = BASE_URL + '/api/bookings';

const book = (booking) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.post(BOOKING_URL + '/book', JSON.parse(JSON.stringify(booking)), {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/booking';
			},
			(error) => {
				// display error message that booking failed
			}
		);
};

const assignWorker = (data) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.post(BOOKING_URL + '/assign', JSON.parse(JSON.stringify(data)), {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/admin-booking';
			},
			(error) => {}
		);
};

const addTimeslot = (newBooking) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.post(BOOKING_URL + '/add', JSON.parse(JSON.stringify(newBooking)), {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/admin-booking';
			},
			(error) => {
				// display error message that booking failed
			}
		);
};

export default {
	book,
	addTimeslot,
	assignWorker,
};
