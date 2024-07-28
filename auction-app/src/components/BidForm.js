import React, { useState } from 'react';
import { createBid } from '../services/api';
import { Form, Button, ButtonGroup } from 'react-bootstrap';

const BidForm = ({ itemId, onBidPlaced }) => {
    const [amount, setAmount] = useState('');
    const [userEmail, setUserEmail] = useState('');
    const [paymentMethod, setPaymentMethod] = useState('Credit Card');

    const handleSubmit = (event) => {
        event.preventDefault();
        createBid(itemId, { amount, userEmail, paymentMethod })
            .then(response => {
                alert('Bid placed successfully!');
                setAmount('');
                setUserEmail('');
                setPaymentMethod('Credit Card');
                onBidPlaced();
            })
            .catch(error => {
                alert(`Failed to place bid: ${error.response ? error.response.data.message : error.message}`);
            });
    };

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formBidAmount">
                <Form.Label>Amount</Form.Label>
                <Form.Control
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="Enter bid amount"
                />
            </Form.Group>
            <Form.Group controlId="formUserEmail">
                <Form.Label>User Email</Form.Label>
                <Form.Control
                    type="email"
                    value={userEmail}
                    onChange={(e) => setUserEmail(e.target.value)}
                    placeholder="Enter user email"
                />
            </Form.Group>
            <Form.Group controlId="formPaymentMethod">
                <Form.Label>Payment Method</Form.Label>
                <ButtonGroup>
                    <Button
                        variant={paymentMethod === 'Credit Card' ? 'primary' : 'secondary'}
                        onClick={() => setPaymentMethod('Credit Card')}
                    >
                        Credit Card
                    </Button>
                    <Button
                        variant={paymentMethod === 'PayPal' ? 'primary' : 'secondary'}
                        onClick={() => setPaymentMethod('PayPal')}
                    >
                        PayPal
                    </Button>
                    <Button
                        variant={paymentMethod === 'Bank Transfer' ? 'primary' : 'secondary'}
                        onClick={() => setPaymentMethod('Bank Transfer')}
                    >
                        Bank Transfer
                    </Button>
                </ButtonGroup>
            </Form.Group>
            <Button variant="primary" type="submit">
                Place Bid
            </Button>
        </Form>
    );
}

export default BidForm;
