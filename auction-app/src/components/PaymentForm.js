import React, { useState } from 'react';
import axios from 'axios';

const PaymentForm = () => {
    const [amount, setAmount] = useState('');
    const [transactionId, setTransactionId] = useState('');
    const [paymentUrl, setPaymentUrl] = useState('');
    const [paymentInfo, setPaymentInfo] = useState({});

    const handlePayment = async () => {
        try {
            console.log('Sending payment request with amount:', amount);
            const response = await axios.post('http://localhost:8081/api/payments/bitcoin/create', null, { params: { amount } });
            console.log('Payment created:', response.data);
            setTransactionId(response.data.transactionId);
            setPaymentUrl(response.data.paymentUrl);
            setPaymentInfo(response.data);
        } catch (error) {
            console.error('Error creating transaction', error);
        }
    };

    const handleAcceptance = async () => {
        try {
            console.log('Accepting payment for transaction ID:', transactionId);
            const response = await axios.post('http://localhost:8081/api/payments/bitcoin/accept', null, { params: { transactionId } });
            console.log('Payment accepted:', response.data);
            alert(`Payment accepted for transaction ID: ${transactionId}`);
            onBidUpdate(amount);
            window.location.href = '/';
        } catch (error) {
            console.error('Error accepting transaction', error);
        }
    }

    return (
        <div>
            <h2>Make a Payment</h2>
            <input
                type="number"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="Enter amount"
            />
            <button onClick={handlePayment}>Pay with Bitcoin</button>
            {transactionId && (
                <div>
                    <p>Transaction ID: {transactionId}</p>
                    <p>Payment URL: <a href={paymentUrl} target="_blank" rel="noopener noreferrer">{paymentUrl}</a></p>
                    <p>Status: {paymentInfo.status}</p>
                    <button onClick={handleAcceptance}>Accept Payment</button>
                </div>
            )}
        </div>
    );
};


export default PaymentForm;
