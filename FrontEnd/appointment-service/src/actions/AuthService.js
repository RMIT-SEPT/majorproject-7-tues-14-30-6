import axios from 'axios';

const API_URL = 'http://localhost:8080';

// register an account
const register = (newAccount) => {
	axios.post(API_URL + '/api/auth/register', JSON.parse(JSON.stringify(newAccount))).then(
		(response) => {
			window.location.href = '/login';
		},
		(error) => {
			document.getElementById('status').style.display = 'block';
			document.getElementById('message').innerHTML = error.message;
		}
	);
};

const registerWorker = (newAccount) => {
	let user = JSON.parse(sessionStorage.getItem('user'));
	let tokenType = user.tokenType;
	let accessToken = user.accessToken;
	let authorization = tokenType + ' ' + accessToken;

	axios
		.post(API_URL + '/api/workers/add', JSON.parse(JSON.stringify(newAccount)), {
			headers: {
				Authorization: authorization,
			},
		})
		.then(
			(response) => {
				window.location.href = '/worker';
			},
			(error) => {
				document.getElementById('status').style.display = 'block';
				document.getElementById('message').innerHTML = error.message;
			}
		);
};

// login
const login = (account) => {
	axios
		.post(API_URL + '/api/auth/login', JSON.parse(JSON.stringify(account)))
		.then((response) => {
			sessionStorage.setItem('user', JSON.stringify(response.data));
			window.location.href = '/ ';
		})
		.catch((error) => {
			document.getElementById('status').style.display = 'none';

			let message = '';

			if (error.response) {
				document.getElementById('status').style.display = 'block';
				if (error.response.status === 401) {
					message = 'Invalid login credentials';
				} else {
					message = error.response.data.message;
				}
				document.getElementById('message').innerHTML = message;
			}
		});
};

// put the other const in here as an array
export default {
	register,
	login,
	registerWorker,
};
