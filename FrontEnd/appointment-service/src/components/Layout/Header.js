import React, { Component } from 'react'

class Header extends Component {
    render() {
        return (
            <div>
                <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
                    <div className="container">
                        <a className="navbar-brand" href="/">
                            AGME
                     </a>
                        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#mobile-nav">
                            <span className="navbar-toggler-icon" />
                        </button>

                        <div className="collapse navbar-collapse" id="mobile-nav">
                            <ul className="navbar-nav mr-auto">
                                {/* <li className="nav-item">
                                    <a className="nav-link" href="/dashboard">
                                        Dashboard
                                    </a>
                                </li> */}

                                <li className="nav-item">
                                    <a className="nav-link" href="/booking">
                                        Bookings
                                    </a>
                                </li>
                            </ul>

                            {/* start of button groups */}
                            {sessionStorage.getItem("user")
                                ?
                                <ul className="navbar-nav ml-auto">
                                    <li className="nav-item">
                                        <a className="nav-link" href ="" onClick={() => sessionStorage.clear()}>
                                            Log out as {JSON.parse(sessionStorage.getItem("user")).username}
                                        </a>
                                    </li>
                                </ul>
                                :
                                <ul className="navbar-nav ml-auto">
                                    <li className="nav-item">
                                        <a className="nav-link" href="/login">
                                            Login
                                    </a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link " href="/signup">
                                            Sign Up
                            </a>
                                    </li>
                                </ul>
                            }
                            {/*End of button groups */}
                        </div>
                    </div>
                </nav>
            </div>
        )
    }
}
export default Header;