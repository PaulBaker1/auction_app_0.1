import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import { createBid } from '../services/api';

const BidForm = ({ itemId, onBidPlaced }) => {
    const [amount, setAmount] = useState('');
    const [email, setEmail] = useState('');
    const [paymentMethod, setPaymentMethod] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!paymentMethod) {
            setError('Please select a payment method.');
            return;
        }

        const bid = {
            amount: parseFloat(amount),
            email,
            paymentMethod,
        };

        createBid(itemId, bid)
            .then(() => {
                onBidPlaced();
                setAmount('');
                setEmail('');
                setPaymentMethod('');
                setError('');
            })
            .catch(error => {
                console.error('Error placing bid:', error);
                setError('Error placing bid. Please try again.');
            });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-group">
                <label htmlFor="amount">Amount</label>
                <input
                    type="number"
                    id="amount"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="Enter bid amount"
                    required
                />
            </div>
            <div className="form-group">
                <label htmlFor="user-email">User Email</label>
                <input
                    type="email"
                    id="user-email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Enter user email"
                    required
                />
            </div>
            <div className="payment-methods">
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
                    variant={paymentMethod === 'Bitcoin' ? 'primary' : 'secondary'}
                    onClick={() => setPaymentMethod('Bitcoin')}
                >
                    Bitcoin
                </Button>
            </div>
            {error && <p className="error-message">{error}</p>}
            <div className="form-actions">
                <Button variant="primary" type="submit">Place Bid</Button>
            </div>
        </form>
    );
};

export default BidForm;
