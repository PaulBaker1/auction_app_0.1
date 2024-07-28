import React from 'react';
import { Card, Button, ButtonGroup, Form } from 'react-bootstrap';

const AuctionItemEditForm = ({ item, handleChange, handleSubmit }) => {
    const { name, description, price, startingBid } = item;

    return (
        <div className="col-md-8">
            <Card>
                <Card.Body>
                    <Card.Title>Edit Auction Item</Card.Title>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formItemName">
                            <Form.Label>Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="name"
                                value={name}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="formItemDescription" className="mt-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                as="textarea"
                                name="description"
                                value={description}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="formItemPrice" className="mt-3">
                            <Form.Label>Price</Form.Label>
                            <Form.Control
                                type="number"
                                name="price"
                                value={price}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="formStartingBid" className="mt-3">
                            <Form.Label>Starting Bid</Form.Label>
                            <Form.Control
                                type="number"
                                name="startingBid"
                                value={startingBid}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>
                        <ButtonGroup className="mt-3">
                            <Button variant="secondary" onClick={() => handleChange({ target: { name: 'price', value: price * 0.9 } })}>10%</Button>
                            <Button variant="secondary" onClick={() => handleChange({ target: { name: 'price', value: price * 0.75 } })}>25%</Button>
                            <Button variant="secondary" onClick={() => handleChange({ target: { name: 'price', value: price * 0.5 } })}>50%</Button>
                            <Button variant="secondary" onClick={() => handleChange({ target: { name: 'price', value: price * 0.25 } })}>75%</Button>
                        </ButtonGroup>
                        <Button variant="primary" type="submit" className="mt-3">
                            Save Changes
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    );
};

export default AuctionItemEditForm;
