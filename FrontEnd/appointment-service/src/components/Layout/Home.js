import React, { Component } from 'react'
import doctor from "../images/doctors.png";
import './Home.css'
import Card from 'react-bootstrap/Card'
import Image from 'react-bootstrap/Image'

export default class Home extends Component {
    render() {
        return (
            <div id="homePage">
                {/*Title*/}
                <h1 className="display-5 text-center">Welcome to AGME</h1>
                <h5 className="display-5 text-center">Essential booking service for any bussiness</h5>
                {/* end of title */}

                {/* content */}
                {/* Perhaps display inline block? */}
                <div className="block-content">
                    <div className="card bg-primary text-white" style={{ width: '18rem' }}>
                        <Card.Body>
                            <Card.Title>Who we are</Card.Title>
                            <Card.Text>
                                We are a service for busines
                     </Card.Text>
                        </Card.Body>
                    </div>

                    <div className="align-right">
                        <Image src={doctor}  id="doctor" alt="Doctors appointment" rounded />
                    </div>
                </div>
                {/* end of content */}

            </div >
        )
    }
}