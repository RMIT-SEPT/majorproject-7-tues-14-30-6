import React, { useState } from "react";
import './App.css'
import Dashboard from './components/dashboard/Dashboard'
import Header from './components/Layout/Header'
import "bootstrap/dist/css/bootstrap.min.css"
import { BrowserRouter as Router, Route } from "react-router-dom"
import AddPerson from './components/Persons/AddPerson'
import { Provider } from "react-redux"
import store from './store'
import Home from './components/Layout/Home.js'
import Login from './components/login/Login.js'
import Signup from './components/signup/Signup'
import Booking from './components/booking/Booking'
import Worker from './components/worker/Worker'

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div>
          <Header />
          <Route exact path="/" component={Home} />
          <Route exacpt path="/login" component={Login} />
          <Route exacpt path="/signup" component={Signup} />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/addPerson" component={AddPerson} />
          <Route exact path="/booking" component={Booking} />
          <Route exact path="/worker" component={Worker} />
        </div>
      </Router>
    </Provider>
  );
}

export default App;