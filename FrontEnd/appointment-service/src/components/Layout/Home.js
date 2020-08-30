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
                <h1 className="display-5 text-center">AGME</h1>
                <h5 className="display-5 text-center">Essential booking service for any bussiness</h5>
                {/* end of title */}

                {/* content */}
                {/* first inline block */}
                <div className="block-content clearfix">

                        <div className="card text-black d-inline-block card-left" style={{ width: '22rem' }}>
                            <Card.Body>
                                <Card.Title><h3>Who we are</h3></Card.Title>
                                <Card.Text>
                                   <p>Here at AGME, we provide booking services for bussinesses to integrate into their site.
                                       <br></br><br></br>
                                       Through us, you will be able to provide working booking services for your customers.
                                   </p>
                                </Card.Text>
                            </Card.Body>
                        </div>
                    <Image src={doctor} className="d-inline-block float-right" id="doctor" alt="Doctors appointment" rounded />

                </div>
                {/* end of first inline block */}

                {/* second inline block */}
                <div className="block-content clearfix">
                <Image src={doctor} className="d-inline-block float-left" id="doctor" alt="Doctors appointment" rounded />

                <div className="card text-black d-inline-block card-right" style={{ width: '22rem' }}>
                            <Card.Body>
                                <Card.Title><h3>Why choose us</h3></Card.Title>
                                <Card.Text>
                                   <p>
                                    Not only are we one of the cheapest services out there, we also make our booking service 
                                    easy to integrate into your site. 
                                    <br></br>
                                    So you can get your booking service up and running quick and easily.
                                   </p>
                                </Card.Text>
                            </Card.Body>
                        </div>
                    
                </div>
{               /*end of second inline block */} 
                {/* end of content */}
            </div >
        )
    }
}