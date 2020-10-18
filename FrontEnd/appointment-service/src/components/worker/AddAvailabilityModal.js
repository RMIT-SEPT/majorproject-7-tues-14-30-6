import React, { useState } from 'react';
import { Modal, Button, Form, Row, Col } from 'react-bootstrap';
import TimePicker from 'react-time-picker';
import AvailabilityService from '../../actions/AvailabilityService';

const AddAvailabilityModal = (props) => {
	const [ selectedValue, setSelectedValue ] = useState('Monday');
	const [ startTime, handleStartTimeChange ] = useState(null);
	const [ endTime, handleEndTimeChange ] = useState(null);

	const handleSubmit = (e) => {
		e.preventDefault();
		let userId = JSON.parse(sessionStorage.getItem('user')).id;

		const availabilityRequest = {
			workerId: userId,
			data: {
				weekDay: selectedValue,
				startTime: startTime,
				endTime: endTime,
			},
        };
		AvailabilityService.addAvailability(availabilityRequest);
	};

	const handleSelectedValue = (e) => {
		setSelectedValue(e.target.value);
	};

	return (
		<Modal {...props} size="md" aria-labelledby="contained-modal-title-vcenter" centered>
			<Modal.Header closeButton>
				<Modal.Title id="contained-modal-title-vcenter">Add Availability Entry</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<Form onSubmit={handleSubmit}>
					<Form.Group as={Row} controlId="weekDay">
						<Form.Label column sm="2">
							Week Day
						</Form.Label>
						<Col sm="10">
							<Form.Control as="select" value={selectedValue} onChange={handleSelectedValue}>
								<option>Monday</option>
								<option>Tuesday</option>
								<option>Wednesday</option>
								<option>Thursday</option>
								<option>Friday</option>
								<option>Saturday</option>
								<option>Sunday</option>
							</Form.Control>
						</Col>
					</Form.Group>
					<Form.Row>
						<Form.Group as={Col} controlId="startTime">
							<Form.Label className="mr-2">Available Start Time (24hr format)</Form.Label>
							<TimePicker
								hourPlaceholder="HH"
								minutePlaceholder="mm"
								required={true}
								name="bookingTime"
								onChange={handleStartTimeChange}
								value={startTime}
								clearIcon={null}
								format="HH:mm"
								locale="en-AU"
							/>
						</Form.Group>
						<Form.Group as={Col} controlId="endTime">
							<Form.Label className="mr-2">Available End Time (24hr format)</Form.Label>
							<TimePicker
								hourPlaceholder="HH"
								minutePlaceholder="mm"
								required={true}
								name="bookingTime"
								onChange={handleEndTimeChange}
								value={endTime}
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

export default AddAvailabilityModal;
