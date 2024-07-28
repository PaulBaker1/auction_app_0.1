import React, { useState, useEffect } from 'react';
import axios from 'axios';

const TransactionForm = ({ editing, currentTransaction, onSubmit }) => {
    const [transaction, setTransaction] = useState({
        description: '',
        amount: '',
        timestamp: ''
    });

    useEffect(() => {
        if (editing) {
            setTransaction(currentTransaction);
        }
    }, [editing, currentTransaction]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setTransaction({
            ...transaction,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (editing) {
            await axios.put(`/api/transactions/${transaction.id}`, transaction);
        } else {
            await axios.post('/api/transactions', transaction);
        }
        onSubmit();
        setTransaction({
            description: '',
            amount: '',
            timestamp: ''
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Description</label>
                <input
                    type="text"
                    name="description"
                    value={transaction.description}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label>Amount</label>
                <input
                    type="number"
                    name="amount"
                    value={transaction.amount}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label>Timestamp</label>
                <input
                    type="datetime-local"
                    name="timestamp"
                    value={transaction.timestamp}
                    onChange={handleChange}
                    required
                />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
};

export default TransactionForm;
