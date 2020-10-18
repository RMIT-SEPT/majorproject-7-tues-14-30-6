import React, { useState } from 'react';
import { Modal, Button, Form, Row, Col } from 'react-bootstrap';
import DatePicker from 'react-date-picker';
import TimePicker from 'react-time-picker';
import BookingService from '../../actions/BookingService';

const AddBookingModal = (props) => {
	const [ bookingTime, handleTimeChange ] = useState(null);
	const [ bookingDate, handleDateChange ] = useState(null);

	const serviceName = "Dan's Dentist";

	const currentDate = new Date();
	let nextWeek = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);

	// const roundToHour = date => {
	// 	let p = 60 * 60 * 1000; // milliseconds in an hour
	// 	return new Date(Math.round(date.getTime() / p) * p);
	// }

	const handleSubmit = (e) => {
		e.preventDefault();
		let dateTime = new Date(bookingDate);
		let splitTime = bookingTime.split(':');
		dateTime.setHours(splitTime[0]);
		dateTime.setMinutes(splitTime[1]);

		const newBooking = {
			serviceName: serviceName,
			bookingDateTime: dateTime,
		};
		BookingService.addTimeslot(newBooking);
	};

	return (
		<Modal {...props} size="md" aria-labelledby="contained-modal-title-vcenter" centered>
			<Modal.Header closeButton>
				<Modal.Title id="contained-modal-title-vcenter">Add Booking Timeslot</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<Form onSubmit={handleSubmit}>
					<Form.Group as={Row} controlId="serviceName">
						<Form.Label column sm="2">
							Service Name
						</Form.Label>
						<Col sm="10">
							<Form.Control plaintext readOnly defaultValue={serviceName} />
						</Col>
					</Form.Group>
					<Form.Row>
						<Form.Group as={Col} controlId="bookingDate">
							<Form.Label className="mr-2">Booking Date</Form.Label>
							<DatePicker
								monthPlaceholder="mm"
								dayPlaceholder="dd"
								yearPlaceholder="yyyy"
								required={true}
								name="bookingDate"
								minDate={nextWeek}
								clearIcon={null}
								onChange={handleDateChange}
								value={bookingDate}
								locale="en-AU"
							/>
						</Form.Group>
						<Form.Group as={Col} controlId="bookingTime">
							<Form.Label className="mr-2">Booking Time (24hr format)</Form.Label>
							<TimePicker
								hourPlaceholder="HH"
								minutePlaceholder="mm"
								required={true}
								name="bookingTime"
								onChange={handleTimeChange}
								value={bookingTime}
								clearIcon={null}
								format="HH:mm"
								locale="en-AU"
							/>
						</Form.Group>
					</Form.Row>

					<Modal.Footer>
						<Button variant="primary" type="submit">
							Submit
						</Button>
						<Button variant="secondary" onClick={props.onHide}>
							Close
						</Button>
					</Modal.Footer>
				</Form>
			</Modal.Body>
		</Modal>
	);
};

export default AddBookingModal;
