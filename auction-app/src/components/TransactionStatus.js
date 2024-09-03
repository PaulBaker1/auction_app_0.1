import React, { useState, useEffect } from 'react';
import axios from 'axios';

const TransactionStatus = ({ transactionId }) => {
    const [status, setStatus] = useState('');

    useEffect(() => {
        const fetchStatus = async () => {
            try {
                const response = await axios.get(`/api/payments/bitcoin/status/${transactionId}`);
                setStatus(response.data.status);
            } catch (error) {
                console.error('Error fetching transaction status', error);
            }
        };

        fetchStatus();
    }, [transactionId]);

    return (
        <div>
            <h2>Transaction Status</h2>
            <p>Transaction ID: {transactionId}</p>
            <p>Status: {status}</p>
        </div>
    );
};

export default TransactionStatus;
