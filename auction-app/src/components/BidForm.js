import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import { createBid } from '../services/api';
import axios from 'axios';

const BidForm = ({ itemId, onBidPlaced }) => {
    const [amount, setAmount] = useState('');
    const [email, setEmail] = useState('');
    const [paymentMethod, setPaymentMethod] = useState('');
    const [error, setError] = useState('');
    const [transactionId, setTransactionId] = useState('');
    const [paymentUrl, setPaymentUrl] = useState('');
    const [paymentInfo, setPaymentInfo] = useState({});

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!paymentMethod) {
            setError('Please select a payment method.');
            return;
        }

        if (paymentMethod === 'Bitcoin') {
            handleBitcoinPayment();
        } else {
            const bid = {
                amount: parseFloat(amount),
                email,
                paymentMethod,
            };

            createBid(itemId, bid)
                .then(() => {
                    onBidPlaced(parseFloat(amount));
                    setAmount('');
                    setEmail('');
                    setPaymentMethod('');
                    setError('');
                })
                .catch(error => {
                    console.error('Error placing bid:', error);
                    setError('Error placing bid. Please try again.');
                });
        }
    };

    const handleBitcoinPayment = async () => {
        try {
            console.log('Sending payment request with amount:', amount);
            const response = await axios.post('http://localhost:8081/api/payments/bitcoin/create', null, { params: { amount } });
            console.log('Payment created:', response.data);
            setTransactionId(response.data.transactionId);
            setPaymentUrl(response.data.paymentUrl);
            setPaymentInfo(response.data);
        } catch (error) {
            console.error('Error creating transaction', error);
            setError('Error creating transaction. Please try again.');
        }
    };

    const handleAcceptance = async () => {
        try {
            console.log('Accepting payment for transaction ID:', transactionId);
            const response = await axios.post('http://localhost:8081/api/payments/bitcoin/accept', null, { params: { transactionId } });
            console.log('Payment accepted:', response.data);
            alert(`Payment accepted for transaction ID: ${transactionId}`);
            onBidPlaced(parseFloat(amount)); // Update the bid counter
            // Reset form fields
            setAmount('');
            setEmail('');
            setPaymentMethod('');
            setError('');
            setTransactionId('');
            setPaymentUrl('');
            setPaymentInfo({});
        } catch (error) {
            console.error('Error accepting transaction', error);
            setError('Error accepting transaction. Please try again.');
        }
    };

    return (
        <div>
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
            {transactionId && (
                <div>
                    <p>Transaction ID: {transactionId}</p>
                    <p>Payment URL: <a href={paymentUrl} target="_blank" rel="noopener noreferrer">{paymentUrl}</a></p>
                    <p>Status: {paymentInfo.status}</p>
                    <Button onClick={handleAcceptance}>Accept Payment</Button>
                </div>
            )}
        </div>
    );
};

export default BidForm;
