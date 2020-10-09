import React, { Component } from 'react'

export default class Worker extends Component {
    render() {
        // display error message if not an admin
        if (!(sessionStorage.getItem("user") && JSON.parse(sessionStorage.getItem("user")).roles.includes("ROLE_ADMIN", 0))) {
            return (
                <div id="main">
                    <h4 className="display-5 text-center">Only admins can use this page to add workers!</h4>
                </div>
            )
        }

        return (
            <div id="main">
                <h2 className="display-5 text-center">Workers</h2>
                <h6 className="display-5 text-center">On this page, you can add workers for clients to book through them</h6>

            </div>
        )
    }
}
