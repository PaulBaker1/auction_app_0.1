import React, { useState } from 'react';
import axios from 'axios';

const PaymentForm = ({ setTransactionId, setPaymentUrl }) => {
    const [amount, setAmount] = useState('');
    const [paymentInfo, setPaymentInfo] = useState(null);

    const handlePayment = async () => {
        try {
            const response = await axios.post('http://localhost:8081/api/payments/bitcoin/create', null, { params: { amount } });
            setTransactionId(response.data.transactionId);
            setPaymentUrl(response.data.paymentUrl);
            setPaymentInfo(response.data);
        } catch (error) {
            console.error('Error creating transaction', error);
        }
    };

    return (
        <div>
            <h2>Make a Bitcoin Payment</h2>
            <input
                type="number"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="Amount in USD"
            />
            <button onClick={handlePayment}>Pay with Bitcoin</button>
            {paymentInfo && (
                <div>
                    <h3>Payment Information</h3>
                    <p>Transaction ID: {paymentInfo.transactionId}</p>
                    <p>Status: {paymentInfo.status}</p>
                    <p>Payment URL: <a href={paymentInfo.paymentUrl} target="_blank" rel="noopener noreferrer">{paymentInfo.paymentUrl}</a></p>
                </div>
            )}
        </div>
    );
};

export default PaymentForm;
