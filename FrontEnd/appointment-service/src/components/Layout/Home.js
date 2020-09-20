import React, { Component } from 'react'
import doctor from "../images/doctors.png";
import Image from 'react-bootstrap/Image'
import Card from 'react-bootstrap/Card'
import laptop from "../images/laptop_booking.jpg";
import handshake from "../images/handshake.jpg";
import "bootstrap/dist/css/bootstrap.min.css";
import './Home.css'

export default class Home extends Component {
    render() {
        return (
            <div id="homePage">
                {/*Title*/}

                <h1 className="display-5 text-center">AGME</h1>
                <h5 className="display-5 text-center">Essential booking service for any business</h5>
                {/* end of title */}

                {/* content */}
                {/* first inline block */}
                <div className="block-content clearfix">

                    <Card className="bg-primary text-white d-inline-block card-left" style={{ width: '22rem' }}>
                        <Card.Body>
                            <Card.Title><h3>Who we are</h3></Card.Title>
                            <Card.Text>
                                <p>Here at AGME, we provide booking services for businesses to integrate into their site.
                                       <br></br><br></br>
                                       Through us, you will be able to provide working booking services for your customers.
                                   </p>
                            </Card.Text>
                        </Card.Body>
                    </Card>
                    <Image src={doctor} className="d-inline-block float-right image" alt="Doctors appointment" rounded />

                </div>
                {/* end of first inline block */}

                {/* second inline block */}
                <div className="block-content clearfix">
                    <Image src={laptop} className="d-inline-block float-left image" alt="Laptop booking" rounded />

                    <Card className="bg-primary text-white d-inline-block card-right" style={{ width: '22rem' }}>
                        <Card.Body>
                            <Card.Title><h3>Why choose us</h3></Card.Title>
                            <Card.Text>
                                <p>
                                    Not only are we one of the cheapest services out there, we also make our booking service
                                    easy to integrate into your site.
                                    <br></br><br></br>
                                    So you can get your booking service up and running quickly and easily.
                                   </p>
                            </Card.Text>
                        </Card.Body>
                    </Card>

                </div>
                {/*end of second inline block */}

                {/* start of third inline block */}
                <div className="block-content clearfix">
                    <Card className="bg-primary text-white d-inline-block card-left" style={{ width: '22rem' }}>
                        <Card.Body>
                            <Card.Title><h3>Get started</h3></Card.Title>
                            <Card.Text>
                                <p>
                                    First register an account if you haven't already.
                                    <br></br><br></br>
                                    Once signed in, visit the "Bookings" page to make your booking.
                                </p>
                            </Card.Text>
                        </Card.Body>
                    </Card>
                    <Image src={handshake} className="d-inline-block float-right image" alt="Handshake" rounded />

                </div>
                {/* end of third inline block */}

                {/* end of content */}
            </div >
        )
    }
}