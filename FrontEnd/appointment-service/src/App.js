import React from 'react';
import './App.css';
import Header from './components/Layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import Home from './components/Layout/Home';
import Login from './components/login/Login';
import Signup from './components/signup/Signup';

import CustomerBooking from './components/booking/CustomerBooking';

import AdminWorker from './components/worker/AdminWorker';
import AdminBooking from './components/booking/AdminBooking';

import Availability from './components/worker/Availability';
import WorkerBooking from './components/worker/WorkerBooking';

function App() {
	return (
		<Provider store={store}>
			<Router>
				<div>
					<Header />
					<Route exact path="/" component={Home} />
					<Route exacpt path="/login" component={Login} />
					<Route exact path="/signup" component={Signup} />

					<Route exact path="/booking" component={CustomerBooking} />

					<Route exact path="/admin-booking" component={AdminBooking} />
					<Route exact path="/worker" component={AdminWorker} />

					<Route exact path="/worker-booking" component={WorkerBooking} />
					<Route exact path="/availability" component={Availability} />
				</div>
			</Router>
		</Provider>
	);
}

export default App;
